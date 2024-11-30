package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.flashnotes.flashnotes.Model.Deck;

import java.io.IOException;



public class ViewDecksAddController extends ViewDecksMainMenuFXController {

    @FXML
    private Button deckOneAdd;
    @FXML
    private Button deckTwoAdd;
    @FXML
    private Button deckThreeAdd;
    @FXML
    private Button deckFourAdd;
    @FXML
    private Button deckFiveAdd;
    @FXML
    private Button deckSixAdd;


    // Executes when user clicks to select the deck for editing
    public void addDeck(Event event) throws IOException {
        Object source = event.getSource();

        // Assigns the chosen deck a unique id to keep track of the deck
        if(source == deckOneAdd)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(0);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckTwoAdd)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(1);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckThreeAdd)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(2);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckFourAdd)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(3);
            fireBaseActions.setCurrentDeck(d);
        }
        else if(source == deckFiveAdd)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(4);
            fireBaseActions.setCurrentDeck(d);
        }
        else if (source == deckSixAdd)
        {
            Deck d = fireBaseActions.getCurrentUser().getDecks().get(5);
            fireBaseActions.setCurrentDeck(d);
        }


        // If a deck is assigned an id, it will load the MakeCard screen and pass the deckId to its controller
        // makeCardController is commented out until makeCard gets a controller
        if(currentSelectedDeckId != null)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MakeCard.fxml"));
            Parent makeCardView = fxmlLoader.load();
            // makeCardController makeCardController = fxmlLoader.getController();
            // makeCardController.setDeckId(currentSelectedDeckId);
            anchorPane.getScene().setRoot(makeCardView);
        }
    }
}
