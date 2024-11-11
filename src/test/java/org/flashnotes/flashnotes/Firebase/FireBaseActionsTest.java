package org.flashnotes.flashnotes.Firebase;

import com.google.firebase.auth.FirebaseAuthException;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
class FireBaseActionsTest {

    FireBaseActions actions;
    User user;

    @BeforeEach
    void setUp() {
        actions = FireBaseActions.init();

    }


    @Test
    void register()  {
        try {
            actions.Register("mryankeee", "test2@gmail.com", "password", new File("src/test/java/org/flashnotes/flashnotes/Firebase/MenuIcon.png"));
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assertions.fail("error with registering");
        }

    }


    @Test
    void login() {
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        user = actions.getCurrentUser();
        System.out.println(user);
        Assertions.assertNotNull(user);

    }



    @Test
    void uploadDeck() throws ExecutionException, InterruptedException {
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }

        user = actions.getCurrentUser();
        int initalSize = user.getDecks().size();
        actions.uploadDeck("MathStudy","Mathematics",user.getUsername(), user.getId());

        assertEquals(initalSize+1,user.getDecks().size());
    }

    @Test
    void updateImgTest() throws IOException {
        try {
            // Login the user
            actions.login("test2@gmail.com", "password");
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assertions.fail("Login failed");
        }

        // Get the current user and assert the profile image URL before update
        user = actions.getCurrentUser();
        Assertions.assertNotNull(user);


        // Set a new image (you should have an image file available for testing)
        File newImgFile = new File("src/test/java/org/flashnotes/flashnotes/Firebase/smiley-face-1-20.png");
        String newUrl = actions.updateImg(newImgFile);
        System.out.println(newUrl);
        Assertions.assertNotNull(newUrl);




    }


    @Test
    void updateUsername() {
    }

    @Test
    void updateDeck() throws ExecutionException, InterruptedException {
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        user = actions.getCurrentUser();
        user.getDecks().get(0).setNameOfDeck("english");
        user.getDecks().get(0).getCards().add(new Card("why","oh thats why"));
        actions.updateDeck();
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }


}