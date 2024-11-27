package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class ViewDecksEditController extends ViewDecksMainMenuFXController {

    @FXML
    private Button deckOneEdit;
    @FXML
    private Button deckTwoEdit;
    @FXML
    private Button deckThreeEdit;
    @FXML
    private Button deckFourEdit;
    @FXML
    private Button deckFiveEdit;
    @FXML
    private Button deckSixEdit;



    // Executes when user clicks to select the deck for editing
    public void editDeck(Event event) throws IOException {
        Object source = event.getSource();

        // Assigns the chosen deck a unique id to keep track of the deck
        if(source == deckOneEdit)
        {
            setCurrentSelectedDeckId("deck1");
        }
        else if(source == deckTwoEdit)
        {
            setCurrentSelectedDeckId("deck2");
        }
        else if(source == deckThreeEdit)
        {
            setCurrentSelectedDeckId("deck3");
        }
        else if(source == deckFourEdit)
        {
            setCurrentSelectedDeckId("deck4");
        }
        else if(source == deckFiveEdit)
        {
            setCurrentSelectedDeckId("deck5");
        }
        else if (source == deckSixEdit)
        {
            setCurrentSelectedDeckId("deck6");
        }

        // If a deck is assigned an id, it will enable editing
        if(currentSelectedDeckId != null)
        {
            // It would load a screen?
            // It would access the database to pull the saved deck and could allow the user
            // to traverse through it and alter the text?
        }
    }
}
