package org.flashnotes.flashnotes.Model;

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

        return cards;
    }
    public void setCategory(String category){
        this.category = category;

    }
    public void setNameOfDeck(String nameOfDeck){
        this.nameOfDeck = nameOfDeck;

    }
    public String getCategory(){

        return category;
    }
    public String getOwnerOfDeck(){

        return ownerOfDeck;
    }
    public String getNameOfDeck(){

        return nameOfDeck;
    }
    public String[] getSharedUsers(){

        return sharedUsers;
    }
    public void  addSharedUser(String username){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}




