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


    public void  SetUsername(String username){}

    public void getUsername(){

    }
    public Image getIml(){

    }
    public void setImg(File img){

    }
    public void addDeck(String nameOfDeck){

    }
    public Deck[] getDeck(){

    }
    public void removeDeck(String nameOfDeck){

    }
    public void removeFromRequest(int idOfDeck){

    }
    public void shareDeck(int idOfDeck, String sharedEmail){}



}
