package org.flashnotes.flashnotes.Model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards;
    private List<String> sharedUsers;
    private String ownerOfDeck;
    private String nameOfDeck;
    private String category;
    private String id;

    public Deck (String ownerOfDeck, String nameOfDeck, String category) {
        this.ownerOfDeck = ownerOfDeck;
        this.nameOfDeck = nameOfDeck;
        this.category = category;
        this.cards = new ArrayList<>();
        this.sharedUsers = new ArrayList<>();
        this.id = "";

    }
    public void addCard(String front, String back) {

    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setSharedUsers(ArrayList<String> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public void setOwnerOfDeck(String ownerOfDeck) {
        this.ownerOfDeck = ownerOfDeck;
    }

    public void removeCard(int index){

    }
    public List<Card> getCards(){

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
    public List<String> getSharedUsers(){

        return sharedUsers;
    }
    public void  addSharedUser(String username){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                ", sharedUsers=" + sharedUsers +
                ", ownerOfDeck='" + ownerOfDeck + '\'' +
                ", nameOfDeck='" + nameOfDeck + '\'' +
                ", category='" + category + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}




