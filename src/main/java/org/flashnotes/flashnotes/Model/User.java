package org.flashnotes.flashnotes.Model;

import javafx.scene.image.Image;
import org.flashnotes.flashnotes.ViewModel.SharedDeckDTO;

import java.io.File;
import java.util.List;
import java.util.List;

public class User {
    private String id;
    private String email;
    private String username;
    private Image img;
    private List<Deck> decks;
    private List<String> sharedDecks;
    private List<String> request;

    public User(String id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.img = null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public void setSharedDecks(List<String> sharedDecks) {
        this.sharedDecks = sharedDecks;
    }

    public void setRequest(List<String> request) {
        this.request = request;
    }

    public String getId() {
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

    public List<Deck> getDecks() {
        return decks;
    }

    public List<String> getSharedDecks() {
        return sharedDecks;
    }

    public List<String> getRequest() {
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
