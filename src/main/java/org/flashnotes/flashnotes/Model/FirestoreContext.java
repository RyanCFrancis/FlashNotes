package org.flashnotes.flashnotes.Model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreContext {

    public Firestore FirestoreContext() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    //.setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("Key.json")))
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("Key.json")))
                    .setStorageBucket("flash-notes-74382.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase is initialized");
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        return FirestoreClient.getFirestore();
    }
}
