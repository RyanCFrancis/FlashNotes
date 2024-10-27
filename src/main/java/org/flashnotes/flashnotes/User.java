package org.flashnotes.flashnotes;

import javafx.scene.image.Image;

import java.io.File;

public class User {
    private int id;
    private String email;
    private String username;
    private Image img;
    private Deck[] decks;
    private SharedDeckDTO[] sharedDecks;
    private SharedDeckDTO[] request;

    public User(int id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.img = null;
        this.decks = new Deck[0];
        this.sharedDecks = new SharedDeckDTO[0];
        this.request = new SharedDeckDTO[0];
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Image getImg() {
        return img;
    }

    public Deck[] getDecks() {
        return decks;
    }

    public SharedDeckDTO[] getSharedDecks() {
        return sharedDecks;
    }

    public SharedDeckDTO[] getRequest() {
        return request;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImg(File imgFile) {

    }

    public void addDeck(Deck deck) {

    }

    public void removeDeck(String nameOfDeck) {
    }

    public void removeFromRequest(int idOfDeck) {

    }

    public void shareDeck(int idOfDeck, String sharedEmail) {

    }
}
