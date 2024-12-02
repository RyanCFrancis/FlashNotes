package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;
import java.util.List;


public class ViewDecksAddController extends ViewDecksMainMenuFXController {


    @FXML
    private StackPane StackPaneOne;

    @FXML
    private StackPane StackPaneTwo;

    @FXML
    private StackPane StackPaneThree;

    @FXML
    private StackPane StackPaneFour;

    @FXML
    private StackPane StackPaneFive;

    @FXML
    private StackPane StackPaneSix;

    @FXML
    private Label DeckFiveLabel;

    @FXML
    private Label DeckFourLabel;

    @FXML
    private Label DeckOneLabel;

    @FXML
    private Label DeckSixLabel;

    @FXML
    private Label DeckThreeLabel;

    @FXML
    private Label DeckTwoLabel;



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
    private Deck d;

    @FXML
    void changeToHand(MouseEvent event) {
        try {
            deckSixAdd.getScene().setCursor(Cursor.HAND);
        } catch (Exception e) {

        }
        }

    @FXML
    void changeBack(MouseEvent event) {
        try {
            deckSixAdd.getScene().setCursor(Cursor.DEFAULT);
        }catch (Exception e){

        }
        }
    @FXML
    public void initialize() {
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            deckSixAdd.getScene().setCursor(Cursor.DEFAULT);
        });

        fbActions = FireBaseActions.init();
        deckPicked = false;

        // Gets the deck names and checks to see if the deck name is null
        List<Deck> decks = fbActions.getCurrentUser().getDecks().stream()
                .filter(deck -> deck.getNameOfDeck() != null && !deck.getNameOfDeck().trim().isEmpty())
                .toList();

        // If deck exists, it will set text to deck name and stack pane will be visible
        if (decks.size() > 0)
        {
            DeckOneLabel.setText(decks.get(0).getNameOfDeck());
            StackPaneOne.setVisible(true);
        } else {
            StackPaneOne.setVisible(false);
        }

        if (decks.size() > 1) {
            DeckTwoLabel.setText(decks.get(1).getNameOfDeck());
            StackPaneTwo.setVisible(true);
        } else {
            StackPaneTwo.setVisible(false);
        }

        if (decks.size() > 2) {
            DeckThreeLabel.setText(decks.get(2).getNameOfDeck());
            StackPaneThree.setVisible(true);
        } else {
            StackPaneThree.setVisible(false);
        }

        if (decks.size() > 3)
        {
            DeckFourLabel.setText(decks.get(3).getNameOfDeck());
        } else {
            StackPaneFour.setVisible(false);
        }

        if (decks.size() > 4)
        {
            DeckFiveLabel.setText(decks.get(4).getNameOfDeck());
            StackPaneFive.setVisible(true);
        } else {
            StackPaneFive.setVisible(false);
        }

        if (decks.size() > 5)
        {
            DeckSixLabel.setText(decks.get(5).getNameOfDeck());
            StackPaneSix.setVisible(true);
        } else {
            StackPaneSix.setVisible(false);
        }
    }



    // Executes when user clicks to select the deck for editing
    public void addDeck(Event event) throws IOException {
        Object source = event.getSource();
        System.out.println(source.toString());
        // Assigns the chosen deck a unique id to keep track of the deck
        if(source.toString().contains("deckOneAdd"))
        {
            d = fbActions.getCurrentUser().getDecks().get(0);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckTwoAdd"))
        {
            d = fbActions.getCurrentUser().getDecks().get(1);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckThreeAdd"))
        {
            d = fbActions.getCurrentUser().getDecks().get(2);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckFourAdd"))
        {
            d = fbActions.getCurrentUser().getDecks().get(3);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckFiveAdd"))
        {
            d = fbActions.getCurrentUser().getDecks().get(4);
            fbActions.setCurrentDeck(d);
            deckPicked = true;
        }
        else if(source.toString().contains("deckSixAdd"))
        {
            d = fbActions.getCurrentUser().getDecks().get(5);
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
