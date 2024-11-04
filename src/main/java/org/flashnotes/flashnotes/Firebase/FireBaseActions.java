package org.flashnotes.flashnotes.Firebase;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.Service.*;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.StorageClient;

import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.User;
import org.flashnotes.flashnotes.ViewModel.SharedDeckDTO;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

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
            ApiFuture<QuerySnapshot> future = fstore.collection("Users").whereEqualTo("email", email).get();
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            return;
        }

        if (!documents.isEmpty()) {
            QueryDocumentSnapshot document = documents.get(0);  // Since email should be unique, take the first result
            String storedPassword = document.getString("password");

            if (storedPassword != null && storedPassword.equals(password)) {
                currentUser = mapDocumentToUser(document);
            } else {
                throw new IllegalArgumentException("Email or password is incorrect.");
            }
        } else {
            throw new IllegalArgumentException("No user found with the provided email.");
        }

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
        data.put("id", user.getUid());
        data.put("email", email);
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

        String blobName = "images/" + img.getName();
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
     * @param ownerOfDeck The owner/creator of the deck.
     * @return The ID of the uploaded deck, or 0 if the upload fails.
     */
    public String uploadDeck(String nameOfDeck, String category, String ownerOfDeck,String userId) throws ExecutionException, InterruptedException {
        // Generate a unique ID for the new deck document
        String deckId = UUID.randomUUID().toString();
        DocumentReference deckRef = fstore.collection("Decks").document(deckId);
        DocumentReference userRef = fstore.collection("Users").document(userId);

        // Create the deck data
        Map<String, Object> deckData = new HashMap<>();
        deckData.put("name", nameOfDeck);
        deckData.put("category", category);
        deckData.put("cards", new ArrayList<Card>());
        deckData.put("owner", userId);
        deckData.put("createdAt", FieldValue.serverTimestamp());

        ApiFuture<DocumentSnapshot> userSnap = userRef.get();
        while(!userSnap.isDone()){
            if(userSnap.isCancelled()){
                throw new RuntimeException("error receive user info");
            }
        }

       DocumentSnapshot snap =  userSnap.get();
        if(snap.exists()){
            // Start a batch operation to upload the deck and update the user document
            WriteBatch batch = fstore.batch();

            // Add the deck data
            batch.set(deckRef, deckData);

            // Update the user's deckIds array with the new deck ID
            batch.update(userRef, "deckIds", FieldValue.arrayUnion(deckId));

            // Commit the batch
            ApiFuture<List<WriteResult>> commited = batch.commit();
            while(!commited.isDone()){
                if(commited.isCancelled()){
                    throw new RuntimeException("error updating info");
                }
            }

            System.out.println(commited.get().toString());


        }

        Deck newDeck = new Deck(ownerOfDeck,nameOfDeck,category);
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
     * Updates the username of a user.
     *
     * @param username The new username to be set for the user.
     * @param id       The ID of the user whose username is to be updated.
     */
    public void updateUsername(String username, int id) {
        // Implementation here
    }

    /**
     * Updates an existing deck with new information.
     * add deck to parameters once class is created
     * @param idOfDeck The ID of the deck to be updated.
     */
    public void updateDeck(int idOfDeck) {
        // Implementation here
    }

    private static String downloadImage(String imageUrl) {
        String fileName;
        try {
            URL url = new URL(imageUrl);
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
    public User mapDocumentToUser(QueryDocumentSnapshot document) {
        // Basic User information
        String id = document.getString("id");
        String email = document.getString("email");
        String username = document.getString("username");
        User user = new User(id, email, username);

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

        // Map shared decks and requests if stored as lists of Strings
        List<String> sharedDecks = (List<String>) document.get("sharedDecks");
        if (sharedDecks != null) {
            user.setSharedDecks(sharedDecks);
        }

        List<String> requests = (List<String>) document.get("request");
        if (requests != null) {
            user.setRequest(requests);
        }

        return user;
    }

    /**
     * Maps a Firestore document representing a deck to a Deck object.
     *
     * @param document The Firestore document snapshot representing a Deck.
     * @return The Deck object populated with data from Firestore.
     */
    private Deck mapDocumentToDeck(DocumentSnapshot document) {
        String owner = document.getString("owner");
        String name = document.getString("name");
        String category = document.getString("category");
        Deck deck = new Deck(owner, name, category);

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


