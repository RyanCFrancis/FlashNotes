package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.flashnotes.flashnotes.Model.Deck;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ViewDecksDeleteController extends ViewDecksMainMenuFXController implements homeButtonInterface {

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


    private boolean deckPicked;
    private Deck d;


    @FXML
    public void intialize(){
        deckPicked = false;
    }

    // If user selects the deck it gets assigned a unique Id
    public void deleteDeck(Event event) throws IOException, ExecutionException, InterruptedException {
        Object source = event.getSource();
//        System.out.println(source.toString());


        if(source.toString().contains("deckOneDelete"))
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(0);
            fireBaseActions.setCurrentDeck(d);
            this.deckPicked = true;
        }
        else if(source.toString().contains("deckTwoDelete"))
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(1);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckThreeDelete"))
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(2);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckFourDelete"))
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(3);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckFiveDelete"))
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(4);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckSixDelete"))
        {
            d = fireBaseActions.getCurrentUser().getDecks().get(5);
            fireBaseActions.setCurrentDeck(d);
            deckPicked = true;
        }


        // If a deck is assigned an id, it will delete a chosen deck

        if(deckPicked)
        {
//            System.out.println(d.toString());
            fireBaseActions.getCurrentUser().getDecks().remove(d);
            fireBaseActions.updateDeck();
            //TODO POPUP SAYING DECK DELETED



            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
            Parent makeCardView = fxmlLoader.load();
            anchorPane.getScene().setRoot(makeCardView);
        }
    }
}
