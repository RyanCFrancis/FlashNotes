package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.flashnotes.flashnotes.Model.Deck;

import java.io.IOException;

public class ViewDecksDeleteController extends ViewDecksMainMenuFXController {

    @FXML
    private Button deckOneDelete;
    @FXML
    private Button deckTwoDelete;
    @FXML
    private Button deckThreeDelete;
    @FXML
    private Button deckFourDelete;
    @FXML
    private Button deckFiveDelete;
    @FXML
    private Button deckSixDelete;



    // If user selects the deck it gets assigned a unique Id
    public void deleteDeck(Event event) throws IOException {
        Object source = event.getSource();


        if(source == deckOneDelete)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(0);
            fireBaseActions.setCurrentDeck(d);;

        }
        else if(source == deckTwoDelete)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(1);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckThreeDelete)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(2);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckFourDelete)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(3);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckFiveDelete)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(4);
            fireBaseActions.setCurrentDeck(d);
        }
        else if (source == deckSixDelete)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(5);
            fireBaseActions.setCurrentDeck(d);
        }


        // If a deck is assigned an id, it will delete a card in the chosen deck

        if(currentSelectedDeckId != null)
        {
            // It would load a screen?
            // It would allow the user to traverse through the deck?
            // Or it would delete the entire deck instead?
        }
    }
}
