package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

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
            setCurrentSelectedDeckId("deck1");

        }
        else if(source == deckTwoDelete)
        {
            setCurrentSelectedDeckId("deck2");
        }
        else if(source == deckThreeDelete)
        {
            setCurrentSelectedDeckId("deck3");
        }
        else if(source == deckFourDelete)
        {
            setCurrentSelectedDeckId("deck4");
        }
        else if(source == deckFiveDelete)
        {
            setCurrentSelectedDeckId("deck5");
        }
        else if (source == deckSixDelete)
        {
            setCurrentSelectedDeckId("deck6");
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
