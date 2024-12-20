package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;

public class ViewDecksEditController extends ViewDecksMainMenuFXController implements  homeButtonInterface{

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

    private Deck d;
    private boolean deckPicked;

//    maybe not needed?
//    public void initialize() {
//        fireBaseActions = FireBaseActions.init();
//    }


    // Executes when user clicks to select the deck for editing
    public void editDeck(Event event) throws IOException {
        Object source = event.getSource();

        // Assigns the chosen deck a unique id to keep track of the deck
        if(source == deckOneEdit)
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(0);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source == deckTwoEdit)
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(1);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source == deckThreeEdit)
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(2);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source == deckFourEdit)
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(3);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source == deckFiveEdit)
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(4);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if (source == deckSixEdit)
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(5);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }

        // If a deck is assigned an id, it will enable editing
        if(deckPicked)
        {
            deckPicked = false;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/EditDeck.fxml"));
            Parent makeCardView = fxmlLoader.load();
            // makeCardController makeCardController = fxmlLoader.getController();
            // makeCardController.setDeckId(currentSelectedDeckId);
            anchorPane.getScene().setRoot(makeCardView);
        }
    }
}
