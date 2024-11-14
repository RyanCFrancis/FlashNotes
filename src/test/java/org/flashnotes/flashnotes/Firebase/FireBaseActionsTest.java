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
        System.out.println(user.getDecks());
        Assertions.assertNotNull(user);

    }

    @Test
    void shareDeck(){
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        actions.shareToUser("mryankeecj@gmail.com", "5f7fa7f9-5420-432b-89b9-d303f3611c9d");


    }

//    @Test
//    void handleRequest() throws ExecutionException, InterruptedException {
//        try {
//            actions.login("test2@gmail.com", "password");
//        }catch (FirebaseAuthException e){
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//
//        }
//
//        actions.handleRequest(false,"081cf83d-c4dd-4563-babf-0ee16eb7959b");
//
//    }



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
        System.out.println(user.getDecks());
        actions.uploadDeck("MathStudy","Mathematics",user.getUsername(), user.getId());
        actions.uploadDeck("MathStudy","Mathematics",user.getUsername(), user.getId());

        assertEquals(initalSize+2,user.getDecks().size());
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
       user.getDecks().remove(0);
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