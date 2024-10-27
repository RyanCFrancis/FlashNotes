package org.flashnotes.flashnotes;

public class Deck {

    private Card[] cards;
    private String[] sharedUsers;
    private String ownerOfDeck;
    private String nameOfDeck;
    private String category;
    private int id;

    public Deck (String ownerOfDeck, String nameOfDeck, String category) {
        this.ownerOfDeck = ownerOfDeck;
        this.nameOfDeck = nameOfDeck;
        this.category = category;
        this.cards = new Card[0];
        this.sharedUsers = new String[0];
        this.id = 0;

    }
    public void addCard(String front, String back) {

    }
    public void removeCard(int index){

    }
    public Card[] getCards(){

    }
    public void setCategory(String category){

    }
    public void setNameOfDeck(String nameOfDeck){

    }
    public String getCategory(){

    }
    public String getOwnerOfDeck(){

    }
    public String getNameOfDeck(){

    }
    public String[] getSharedUsers(){

    }
    public void  addSharedUser(String username){

    }



}
