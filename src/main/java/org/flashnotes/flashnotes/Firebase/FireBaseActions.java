package org.flashnotes.flashnotes.Firebase;


import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.StorageClient;


import java.io.File;

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
    private UserRecord currentUser;


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
            instance = new FireBaseActions();
            return instance;
        }
        return instance;
    }

    /**
     * This class logs in user based on their credentials and throws and error if
     *
     * @param email    email user is attemping to log in with
     * @param password password user is trying to log in with
     * @return User being logged in
     */
    public void login(String email, String password) {

    }

    /**
     * This method registers a new user in Firebase.
     *
     * @param username     The username of the new user.
     * @param email        The email of the new user.
     * @param password     The password for the new user's account.
     * @param profileImage The profile image to be associated with the user.
     * @throws FirebaseAuthException If there is an error during the Firebase authentication process.
     */
    public void Register(String username, String email, String password, File profileImage) throws FirebaseAuthException {
        // Implementation here
    }

    /**
     * This method handles the registration of a new user.
     *
     * @param email    The email address of the user to be registered.
     * @param username The username chosen by the user.
     * @param password The password for the user's account.
     * @param img      The profile image to upload for the user.
     * @return true if registration is successful, false otherwise.
     */
    public boolean register(String email, String username, String password, File img) {
        // Implementation here
        return false;
    }

    /**
     * Uploads an image file to the server.
     *
     * @param img The image file to be uploaded.
     */
    private void uploadImg(File img) {
        // Implementation here
    }

    /**
     * Uploads a new deck to the server.
     *
     * @param nameOfDeck  The name of the deck to be uploaded.
     * @param category    The category of the deck (e.g., subject, type).
     * @param ownerOfDeck The owner/creator of the deck.
     * @return The ID of the uploaded deck, or 0 if the upload fails.
     */
    public int uploadDeck(String nameOfDeck, String category, String ownerOfDeck) {
        // Implementation here
        return 0;
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
}
