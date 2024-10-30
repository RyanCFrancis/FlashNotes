package org.flashnotes.flashnotes.ViewModel;

public class SharedDeckDTO {

    private int idOfDeck;

    public SharedDeckDTO(int idOfDeck) {
        this.idOfDeck = idOfDeck;
    }

    public int getIdOfDeck() {
        return idOfDeck;
    }

    public void setIdOfDeck(int idOfDeck) {
        this.idOfDeck = idOfDeck;
    }
}
