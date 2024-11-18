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
    void registerWithExisitingUser()  {
        try {
            actions.Register("mryankeee", "test2@gmail.com", "password", new File("src/test/java/org/flashnotes/flashnotes/Firebase/MenuIcon.png"));
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        Assertions.fail();

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
        actions.shareToUser("mryankeecj@gmail.com", "f5da29a5-2eb1-450f-9f6e-1dc81f7668f6");


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
    void updateDeckRemoveDeck() throws ExecutionException, InterruptedException {
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        user = actions.getCurrentUser();
        int initialSize = user.getDecks().size();
        System.out.println(initialSize);
       user.getDecks().remove(0);
        actions.updateDeck();
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        user = actions.getCurrentUser();
        System.out.println(user.getDecks().size());
        Assertions.assertEquals(initialSize-1,user.getDecks().size());


    }

    @Test
    void updateDeckInfo() throws ExecutionException, InterruptedException {
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        user = actions.getCurrentUser();
        String intialName = user.getDecks().get(0).getNameOfDeck();
        System.out.println(intialName);
        user.getDecks().get(0).setNameOfDeck("new name");
        actions.updateDeck();
        try {
            actions.login("test2@gmail.com", "password");
        }catch (FirebaseAuthException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        user = actions.getCurrentUser();
        System.out.println(user.getDecks().size());
        Assertions.assertNotEquals(intialName,user.getDecks().get(0).getNameOfDeck());
        Assertions.assertEquals("new name",user.getDecks().get(0).getNameOfDeck());
        user.getDecks().get(0).setNameOfDeck(intialName);
        actions.updateDeck();


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

        assertEquals(newUsername, user.getUsername());
    }




}