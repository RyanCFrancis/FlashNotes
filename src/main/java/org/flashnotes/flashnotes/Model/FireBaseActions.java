package org.flashnotes.flashnotes.Model;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.StorageClient;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Charles Gonzalez Jr
 * This class provides all firebase functionality to the application. Other classes
 * in this application can use this class for actions such as user registration, authenticaion,
 * and modification.
 */
public class FireBaseActions {

    private final FirestoreContext contextFirebase = new FirestoreContext();
    private static FireBaseActions instance = null;
    private Firestore fstore;
    private StorageClient storageClient;
    private FirebaseAuth fauth;
    private User currentUser;
    public Card selectedCard;

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    private Deck currentDeck;


    /**
     * Main constructor for initialization
     */
    private FireBaseActions() {
        fstore = contextFirebase.FirestoreContext();
        fauth = FirebaseAuth.getInstance();
        storageClient = StorageClient.getInstance();
    }


    /**
     * This method initializes only one instance of this class due to following the singleton
     * pattern
     *
     * @return the current instance of this class
     */
    public static FireBaseActions init() {
        if (instance == null) {
            synchronized (FireBaseActions.class) {
                instance = new FireBaseActions();
            }
            return instance;
        }
        return instance;
    }

    /**
     *
     * @return current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * This class logs in user based on their credentials and throws and error if
     *
     * @param email    email user is attemping to log in with
     * @param password password user is trying to log in with
     */
    public void login(String email, String password) throws FirebaseAuthException {

        List<QueryDocumentSnapshot> documents;
        try {
            ApiFuture<QuerySnapshot> future = fstore.collection("Users").whereEqualTo("email", email.toLowerCase()).get();
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            return;
        }

        if (!documents.isEmpty()) {
            QueryDocumentSnapshot document = documents.get(0);  // Since email should be unique, take the first result
            String storedPassword = document.getString("password");

            if (storedPassword != null && storedPassword.equals(password)) {
                currentUser = mapDocumentToUser(document,false);
            } else {
                throw new IllegalArgumentException("Email or password is incorrect.");
            }
        } else {
            throw new IllegalArgumentException("No user found with the provided email.");
        }

    }

    public boolean checkForUser(String email) {

        List<QueryDocumentSnapshot> documents;
        try {
            ApiFuture<QuerySnapshot> future = fstore.collection("Users").whereEqualTo("email", email.toLowerCase()).get();
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            return false;
        }

        if (documents.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * This method registers a new user in Firebase.
     *
     * @param username     The username of the new user.
     * @param email        The email of the new user.
     * @param password     The password for the new user's account.
     * @param profileImage The profile image to be associated with the user.
     * @throws FirebaseAuthException If there is an error during the Firebase authentication process.
     * @throws IllegalArgumentException if the user photo upload does not go through
     */
    public void Register(String username, String email, String password, File profileImage) throws FirebaseAuthException {
        UserRecord user = FirebaseAuth.getInstance().createUser(new UserRecord.CreateRequest().setEmail(email));
        String imgURL = "";
        try {
            imgURL = uploadImg(profileImage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with photo upload");

        }

        DocumentReference docRef = fstore.collection("Users").document(UUID.randomUUID().toString());
        Map<String, Object> data = new HashMap<>();
        data.put("id", docRef.getId());
        data.put("email", email.toLowerCase());
        data.put("username", username);
        data.put("password", password);
        data.put("img", imgURL);
        data.put("deckIds", new ArrayList<String>());
        data.put("sharedDecks", new ArrayList<String>());
        data.put("request", new ArrayList<String>());
        System.out.println("here");
        ApiFuture<WriteResult> result = docRef.set(data);

    }


    /**
     * Uploads an image file to the server.
     *
     * @param img The image file to be uploaded.
     */
    private String uploadImg(File img) throws IOException {

        String blobName = "images/" + img.getName().replace(" ","");
        String mimeType = Files.probeContentType(img.toPath());


        Blob blob = storageClient.bucket().create(blobName,new FileInputStream(img),mimeType);
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        System.out.println("file uploaded");

        return String.format("https://storage.googleapis.com/%s/%s", storageClient.bucket().getName(), blobName);
    }

    /**
     * Uploads a new deck to the server.
     *
     * @param nameOfDeck  The name of the deck to be uploaded.
     * @param category    The category of the deck (e.g., subject, type).
     * @return The ID of the uploaded deck, or 0 if the upload fails.
     */
    public String uploadDeck(String nameOfDeck, String category) throws ExecutionException, InterruptedException {

        String deckId = UUID.randomUUID().toString();
        DocumentReference deckRef = fstore.collection("Decks").document(deckId);
        DocumentReference userRef = fstore.collection("Users").document(currentUser.getId());

        // Create the deck data
        Map<String, Object> deckData = new HashMap<>();
        deckData.put("id", deckRef.getId());
        deckData.put("name", nameOfDeck);
        deckData.put("category", category);
        deckData.put("cards", new ArrayList<Card>());
        deckData.put("sharedUsers", new ArrayList<String>());
        deckData.put("owner", currentUser.getUsername());
        deckData.put("createdAt", FieldValue.serverTimestamp());


        ApiFuture<DocumentSnapshot> userSnap = userRef.get();
        DocumentSnapshot snap = userSnap.get();

        if (snap.exists()) {

            WriteBatch batch = fstore.batch();


            batch.set(deckRef, deckData);

            batch.update(userRef, "deckIds", FieldValue.arrayUnion(deckId));


            batch.commit().get(); // Wait for commit completion
        } else {
            throw new RuntimeException("User document not found");
        }


        Deck newDeck = new Deck(currentUser.getUsername(), nameOfDeck, category);
        newDeck.setId(deckId);


        currentUser.getDecks().add(newDeck);

        return deckId;
    }

    /**
     * Updates the profile image of a user.
     *
     * @param id  The ID of the user whose image is to be updated.
     * @param img The new profile image file.
     */
    public void updateImg(int id, File img) {
        // Implementation here
    }

    /**
     * Updates the username of the currently logged-in user in Firebase.
     *
     * @param newUsername The new username to be set for the user.
     */
    public void updateUsername(String newUsername) throws ExecutionException, InterruptedException {
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        String userId = currentUser.getId(); // Get the ID of the currently logged-in user

        // Get a reference to the user document using the user ID
        DocumentReference userRef = fstore.collection("Users").document(userId);

        // Prepare the update data
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("username", newUsername);
        currentUser.setUsername(newUsername);
        // Perform the update operation
        ApiFuture<WriteResult> future = userRef.update(updateData);

        // Wait for the update to complete
        WriteResult result = future.get();
        System.out.println("Updated username for user ID " + userId + " at " + result.getUpdateTime());
    }

    public void logout(){
        currentUser = null;
    }


    /**
     * Updates all decks for the current user in Firestore to match the current deck list in the user object.
     */
    public void updateDeck() throws ExecutionException, InterruptedException {

        if (currentUser == null) {
            throw new IllegalStateException("No user or decks to update.");
        }
        DocumentReference userRef = fstore.collection("Users").document(currentUser.getId());
        Map<String, Object> updateData = new HashMap<>();
        ArrayList<String> ids = new ArrayList<>();
        for(Deck d: currentUser.getDecks()) {
            ids.add(d.getId());
        }
        updateData.put("deckIds", ids);
        ApiFuture<WriteResult> future = userRef.update(updateData);
        WriteResult result = future.get();

        WriteBatch batch = fstore.batch();

        for (Deck deck : currentUser.getDecks()) {
            System.out.println(deck.getId());
            DocumentReference deckRef = fstore.collection("Decks").document(deck.getId());


            Map<String, Object> deckData = new HashMap<>();
            deckData.put("name", deck.getNameOfDeck());
            deckData.put("category", deck.getCategory());
            deckData.put("cards", deck.getCards());
            deckData.put("owner", currentUser.getUsername());
            deckData.put("updatedAt", FieldValue.serverTimestamp());


            batch.set(deckRef, deckData, SetOptions.merge());
        }


        ApiFuture<List<WriteResult>> commitFuture = batch.commit();
        try {

            commitFuture.get();
            System.out.println("All decks updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating decks: " + e.getMessage());
            throw new RuntimeException("Error updating decks: " + e.getMessage());
        }
    }



    private String downloadImage(String imageUrl) {
        String fileName;
        try {
            URL url = new URL(imageUrl.replace(" ",""));
            fileName = getFileName(url);
            File file = new File(fileName);

            try (InputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream out = new FileOutputStream(file)) {

                byte[] data = new byte[1024];
                int count;
                while ((count = in.read(data, 0, data.length)) != -1) {
                    out.write(data, 0, count);
                }
                System.out.println("Downloaded: " + file.getAbsolutePath());
                currentUser.setImg(file);
                return file.toURI().toString();

            }
        } catch (IOException e) {
            System.err.println("Error downloading image from " + imageUrl + ": " + e.getMessage());
        }

        return "";
    }

    private static String getFileName(URL url) {
        String fileName = url.getPath();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }

    /**
     * Maps Firestore document data to a User object, retrieving related decks, shared decks, and requests.
     *
     * @param document The Firestore document snapshot representing a User.
     * @return The User object populated with data from Firestore.
     */
    public User mapDocumentToUser(QueryDocumentSnapshot document,boolean forShared) {
        // Basic User information
        String id = document.getString("id");
        String email = document.getString("email");
        String username = document.getString("username");
        User user = new User(id, email, username);
        if(!forShared) {
            currentUser = user;
        }
        // Deck IDs associated with the User
        List<String> deckIds = (List<String>) document.get("deckIds");
        List<Deck> decks = new ArrayList<>();

        // Retrieve and populate each Deck based on its ID
        if (deckIds != null) {
            for (String deckId : deckIds) {
                DocumentReference deckRef = fstore.collection("Decks").document(deckId);
                ApiFuture<DocumentSnapshot> future = deckRef.get();
                try {
                    DocumentSnapshot deckSnapshot = future.get();
                    if (deckSnapshot.exists()) {
                        Deck deck = mapDocumentToDeck(deckSnapshot);
                        decks.add(deck);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error retrieving deck with ID " + deckId + ": " + e.getMessage());
                }
            }
        }

        // Convert deck list to array and set in User
        user.setDecks(decks);

        if(!forShared) {
            downloadImage(document.getString("img"));


            // Map shared decks and requests if stored as lists of Strings
            List<String> sharedDecks = (List<String>) document.get("sharedDecks");
            if (sharedDecks != null) {
                user.setSharedDecks(sharedDecks);
            }

            List<String> requests = (List<String>) document.get("request");
            if (requests != null) {
                user.setRequest(requests);
            }
        }

        return user;
    }

    public void shareToUser(String email,String deckID){
        //find the user to share with the email
        List<QueryDocumentSnapshot> documents;
        try {
            ApiFuture<QuerySnapshot> future = fstore.collection("Users").whereEqualTo("email", email).get();
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            throw new RuntimeException("Error retrieving user: " + e.getMessage());
        }
        User otherUser = mapDocumentToUser(documents.get(0),true);
        if(otherUser.getDecks().size() == 10){
            throw new RuntimeException("Other users decks are too full");
        }
        //use the id to get the document reference
        DocumentReference sharedUserReference = fstore.collection("Users").document(documents.get(0).get("id").toString());

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("deckIds", FieldValue.arrayUnion(deckID));
        ApiFuture<WriteResult> future = sharedUserReference.update(updateData);

        //make sure update goes through
        try{
            WriteResult result = future.get();
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }





    }

//    public void handleRequest(boolean accepted, String deckId) throws ExecutionException, InterruptedException {
//        if(!accepted){
//            currentUser.getRequest().remove(deckId);
//            DocumentReference currentUserRef = fstore.collection("Users").document(currentUser.getId());
//            Map<String, Object> updateData = new HashMap<>();
//            updateData.put("request", currentUser.getRequest());
//            ApiFuture<WriteResult> future = currentUserRef.update(updateData);
//            try {
//                WriteResult updated = future.get();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//            return;
//        }
//
//        DocumentReference userRef = fstore.collection("Users").document(currentUser.getId());
//        DocumentReference deckRef = fstore.collection("Decks").document(deckId);
//
//        Map<String, Object> userUpdateData = new HashMap<>();
//        Map<String, Object> deckUpdateData = new HashMap<>();
//        currentUser.getRequest().remove(deckId);
//        userUpdateData.put("request", currentUser.getRequest());
//        userUpdateData.put("deckIds", FieldValue.arrayUnion(deckId));
//
//        ApiFuture<DocumentSnapshot> deck = deckRef.get();
//        currentUser.getDecks().add(mapDocumentToDeck(deck.get()));
//        deckUpdateData.put("sharedUsers", FieldValue.arrayUnion(currentUser.getEmail()));
//
//        ApiFuture<WriteResult> futureDeck = deckRef.update(deckUpdateData);
//        ApiFuture<WriteResult> futureUser = deckRef.update(userUpdateData);
//        try{
//            WriteResult result = futureDeck.get();
//            WriteResult userUpdateResult = futureUser.get();
//        }catch (ExecutionException e) {
//            throw new RuntimeException(e);
//
//        }
//
//
//    }

    /**
     * Maps a Firestore document representing a deck to a Deck object.
     *
     * @param document The Firestore document snapshot representing a Deck.
     * @return The Deck object populated with data from Firestore.
     */
    private Deck mapDocumentToDeck(DocumentSnapshot document) {
        DocumentReference ownerRef = fstore.collection("Users").document(currentUser.getId());
        ApiFuture<DocumentSnapshot> future = ownerRef.get();
        DocumentSnapshot ownerSnapshot;
        try {
            ownerSnapshot = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        String owner = ownerSnapshot.getString("username");
        String name = document.getString("name");
        String category = document.getString("category");
        Deck deck = new Deck(owner, name, category);
        deck.setId(document.getString("id"));
        deck.setSharedUsers((ArrayList<String>) document.get("sharedUsers"));
        deck.setOwnerOfDeck(document.getString("owner"));
        // Map deck cards

        List<Map<String, String>> cardData = (List<Map<String, String>>) document.get("cards");
        List<Card> cards = new ArrayList<>();
        if (cardData != null) {
            for (Map<String, String> cardInfo : cardData) {
                String front = cardInfo.get("front");
                String back = cardInfo.get("back");
                cards.add(new Card(front, back));
            }
        }
        deck.setCards(cards);

        return deck;
    }

}


