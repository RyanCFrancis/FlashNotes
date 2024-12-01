package org.flashnotes.flashnotes.View;

import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDatabase {
        private Map<String, User> users = new ConcurrentHashMap<>();  // Simulates "Users" collection
        private Map<String, Deck> decks = new ConcurrentHashMap<>();  // Simulates "Decks" collection

        private User currentLoggedInUserId;  // Field to store the current logged-in user's ID

        public InMemoryDatabase() {
            preloadUsers();
        }

        /**
         * Preloads the in-memory database with a set of sample users and decks.
         */
        private void preloadUsers() {
            // Create sample users
            User user1 = new User("1", "user1@example.com", "user1");
            User user2 = new User("2", "user2@example.com", "user2");
            User user3 = new User("3", "user3@example.com", "user3");

            // Create sample decks
            Deck deck1 = new Deck("deck1", "Java Basics", "Programming");
            deck1.setId("deck1");
            Deck deck2 = new Deck("deck2", "Data Structures", "Programming");
            deck2.setId("deck2");
            Deck deck3 = new Deck("deck3", "World Capitals", "Geography");
            deck3.setId("deck3");

            // Assign decks to users
            user1.setDecks(new ArrayList<>(Collections.singletonList(deck1)));
            user2.setDecks(new ArrayList<>(Collections.singletonList(deck2)));
            user3.setDecks(new ArrayList<>(Collections.singletonList(deck3)));

            // Populate sharedDecks and request lists for each user
            user1.setSharedDecks(new ArrayList<>(Collections.singletonList(deck2.getId())));  // User 1 has access to User 2's deck
            user2.setRequest(new ArrayList<>(Collections.singletonList(deck3.getId())));  // User 2 has a request for User 3's deck

            // Add users and decks to the in-memory database
            users.put(user1.getId(), user1);
            users.put(user2.getId(), user2);
            users.put(user3.getId(), user3);

            decks.put(deck1.getId(), deck1);
            decks.put(deck2.getId(), deck2);
            decks.put(deck3.getId(), deck3);

            System.out.println("Preloaded users and decks into the in-memory database.");
        }

        /**
         * Sets the current logged-in user by user ID.
         */
        public void login(String userId) {
            if (users.containsKey(userId)) {
                currentLoggedInUserId = users.get(userId);
                System.out.println("User logged in: " + userId);
            } else {
                throw new RuntimeException("User not found with ID: " + userId);
            }
        }

        /**
         * Logs out the current user.
         */
        public void logout() {
            if (currentLoggedInUserId != null) {
                System.out.println("User logged out: " + currentLoggedInUserId);
                currentLoggedInUserId = null;
            } else {
                System.out.println("No user is currently logged in.");
            }
        }

        /**
         * Gets the current logged-in user.
         */
        public User getCurrentLoggedInUser() {
            if (currentLoggedInUserId == null) {
                throw new RuntimeException("No user is currently logged in.");
            }
            return users.get(currentLoggedInUserId);
        }

        /**
         * Adds a user to the in-memory database
         */
        public void addUser(User user) {
            users.put(user.getId(), user);
        }

    /**
     * Adds a new deck for the specified user.
     */
    public String uploadDeck(String userId, String name, String category) {
        User user = users.get(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        String deckId = UUID.randomUUID().toString();
        Deck newDeck = new Deck(userId, name, category);

        // Add the deck to the decks map and user's deck list
        decks.put(deckId, newDeck);
        user.addDeck(newDeck);

        return deckId;
    }

    /**
     * Updates all decks for the current user in the in-memory database to match the user's current deck list.
     */
    public void updateDecks(String userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        for (Deck deck : user.getDecks()) {
            if (decks.containsKey(deck.getId())) {
                // Update the in-memory deck to match current data
                decks.put(deck.getId(), deck);
            } else {
                throw new RuntimeException("Deck not found: " + deck.getId());
            }
        }
//        System.out.println("All decks updated successfully.");
    }

    /**
     * Shares a deck by adding the deck's ID to another user's sharedDecks list.
     */
    public void shareDeck(String deckId, String sharedWithEmail) {
        Deck deck = decks.get(deckId);
        if (deck == null) {
            throw new RuntimeException("Deck not found with ID: " + deckId);
        }

        // Find the user to share with
        User sharedUser = users.values().stream()
                .filter(user -> user.getEmail().equals(sharedWithEmail))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found with email: " + sharedWithEmail));

        if(sharedUser.getRequest() == null) sharedUser.setRequest(new ArrayList<String>());

        sharedUser.getRequest().add(deckId);
//        System.out.println("Deck shared with " + sharedWithEmail);
    }



    /**
     * Processes a request to share a deck by removing the deck ID from the user's request list.
     */
    public void removeFromRequest(String userId, String deckId, boolean accepted) {
        User user = users.get(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }


        if(user.getSharedDecks() == null) user.setSharedDecks(new ArrayList<>());


        if (user.getRequest().contains(deckId)) {
            if (accepted) {
                user.getSharedDecks().add(deckId);  // Add the deckId to sharedDecks if accepted is true
            }
            user.getRequest().remove(deckId);  // Remove the deckId from the request list
            System.out.println("Request removed for deck: " + deckId);
        } else {
            System.out.println("Deck ID not found in request list: " + deckId);
        }
    }

    /**
     * Retrieves a user by ID.
     */
    public User getUser(String userId) {
        return users.get(userId);
    }

    /**
     * Retrieves a deck by ID.
     */
    public Deck getDeck(String deckId) {
        return decks.get(deckId);
    }
}
