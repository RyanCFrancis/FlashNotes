package org.flashnotes.flashnotes.Firebase;

import com.google.firebase.auth.FirebaseAuthException;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
class FireBaseActionsTest {

    FireBaseActions actions;
    User user;

    @BeforeEach
    void setUp() {
        actions = FireBaseActions.init();
        //
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
    void updateImg() {
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

    @Test
    void updateUsername() {
        try {
            // Log in with an existing user
            actions.login("test2@gmail.com", "password");
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assertions.fail("Login failed");
        }

        // Get the current user after login
        user = actions.getCurrentUser();

        // Store the old username for comparison
        String oldUsername = user.getUsername();

        // Update the username
        String newUsername = "newTestUsername";
        try {
            actions.updateUsername(newUsername);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Assertions.fail("Error updating username");
        }

        // Re-fetch the current user to ensure the username was updated
        user = actions.getCurrentUser();

        // Assert that the username has been updated successfully
        assertNotEquals(oldUsername, user.getUsername());
        assertEquals(newUsername, user.getUsername());
    }




}