package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

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

    FireBaseActions fbActions;
    boolean deckPicked;
    @FXML
    public void initialize() {
        fbActions = FireBaseActions.init();
        deckPicked = false;
    }



    // Executes when user clicks to select the deck for editing
    public void addDeck(Event event) throws IOException {
        Object source = event.getSource();
        System.out.println(source.toString());
        // Assigns the chosen deck a unique id to keep track of the deck
        if(source.toString().contains("deckOneAdd"))
        {
            Deck d = fbActions.getCurrentUser().getDecks().get(0);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckTwoAdd"))
        {
            Deck d = fbActions.getCurrentUser().getDecks().get(1);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckThreeAdd"))
        {
            Deck d = fbActions.getCurrentUser().getDecks().get(2);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckFourAdd"))
        {
            Deck d = fbActions.getCurrentUser().getDecks().get(3);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckFiveAdd"))
        {
            Deck d = fbActions.getCurrentUser().getDecks().get(4);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckSixAdd"))
        {
            Deck d = fbActions.getCurrentUser().getDecks().get(5);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }


        // If a deck is assigned an id, it will load the MakeCard screen and pass the deckId to its controller
        // makeCardController is commented out until makeCard gets a controller
        if(deckPicked)
        {
            deckPicked = false;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MakeCard.fxml"));
            Parent makeCardView = fxmlLoader.load();
            // makeCardController makeCardController = fxmlLoader.getController();
            // makeCardController.setDeckId(currentSelectedDeckId);
            anchorPane.getScene().setRoot(makeCardView);
        }
    }
}
