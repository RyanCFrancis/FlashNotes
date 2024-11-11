package org.flashnotes.flashnotes.View;

import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseTest {
    private InMemoryDatabase database;

    @BeforeEach
    void setUp() {
        database = new InMemoryDatabase();
    }

    @Test
    void addUser() {
        User user = new User("4", "newuser@example.com", "newuser");
        database.addUser(user);
        assertEquals(user, database.getUser("4"));
    }

    @Test
    void uploadDeck() {
        String userId = "1";
        String deckName = "Advanced Java";
        String category = "Programming";

        String deckId = database.uploadDeck(userId, deckName, category);
        Deck uploadedDeck = database.getDeck(deckId);

        assertNotNull(uploadedDeck);
        assertEquals(deckName, uploadedDeck.getNameOfDeck());
        assertEquals(category, uploadedDeck.getCategory());
        assertEquals(userId, uploadedDeck.getOwnerOfDeck());
    }

    @Test
    void updateDecks() {
        User user = database.getUser("1");
        Deck updatedDeck = new Deck(user.getId(), "Updated Deck", "Updated Category");
        updatedDeck.setId("deck1"); // Existing deck ID

        user.setDecks(List.of(updatedDeck));
        database.updateDecks(user.getId());

        Deck deckInDatabase = database.getDeck("deck1");
        assertEquals("Updated Deck", deckInDatabase.getNameOfDeck());
        assertEquals("Updated Category", deckInDatabase.getCategory());
    }

    @Test
    void shareDeck() {
        String deckId = "deck1";
        String sharedWithEmail = "user2@example.com";

        database.shareDeck(deckId, sharedWithEmail);

        User sharedUser = database.getUser("2");
        assertTrue(sharedUser.getRequest().contains(deckId));
    }


    @Test
    void removeFromRequest() {
        String userId = "2";
        String deckId = "deck3";
        boolean accepted = true;

        database.removeFromRequest(userId, deckId, accepted);

        User user = database.getUser(userId);
        assertFalse(user.getRequest().contains(deckId));
        assertTrue(user.getSharedDecks().contains(deckId));
    }

    @Test
    void getUser() {
        User user = database.getUser("1");
        assertNotNull(user);
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    void getDeck() {
        Deck deck = database.getDeck("deck1");
        assertNotNull(deck);
        assertEquals("Java Basics", deck.getNameOfDeck());
        assertEquals("Programming", deck.getCategory());
    }

    // Test login functionality
    @Test
    void login() {
        // Login user "1"
        database.login("1");

        // Verify that the current logged-in user is user "1"
        User loggedInUser = database.getCurrentLoggedInUser();
        assertNotNull(loggedInUser);
        assertEquals("user1@example.com", loggedInUser.getEmail());
    }

    // Test logout functionality
    @Test
    void logout() {
        // Log in and then log out
        database.login("1");
        database.logout();

        // Try to get the current logged-in user (should throw exception)
        Exception exception = assertThrows(RuntimeException.class, () -> {
            database.getCurrentLoggedInUser();
        });
        assertEquals("No user is currently logged in.", exception.getMessage());
    }

    // Test getting the current logged-in user when no user is logged in
    @Test
    void getCurrentLoggedInUserWhenNotLoggedIn() {
        // Try to get the current logged-in user without logging in (should throw exception)
        Exception exception = assertThrows(RuntimeException.class, () -> {
            database.getCurrentLoggedInUser();
        });
        assertEquals("No user is currently logged in.", exception.getMessage());
    }
}
