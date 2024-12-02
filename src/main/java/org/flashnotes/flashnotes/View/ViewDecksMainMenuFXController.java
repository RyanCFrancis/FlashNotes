package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;

import java.io.IOException;
import java.util.List;


// There is currently no FireStore or FireBase implementation yet which is why
// FireBase Actions is commented out
// All buttons have functionality, the home button is in the homeButtonInterface
//Three other controllers were created to manage the specific functions of the add, delete, and edit
// When chosen decks get assigned a unique Id which keeps track of it


public class ViewDecksMainMenuFXController implements homeButtonInterface {

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
    private MenuItem exit;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Label AddLabel;

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
    private Label DeleteLabel;

    @FXML
    private Text FlashNotes;

    @FXML
    private Rectangle addLabel;

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    private Label editLabel;

    @FXML
    private Text home;

    @FXML
    private ImageView menuIcon;

    @FXML
    private VBox vBox;

    int currentCardIndex;

    Deck currentDeck;

    User currentUser;

    protected String currentSelectedDeckId;

    FireBaseActions fireBaseActions;


    @FXML
    void changeToHand(MouseEvent event) {
        try {
            menuIcon.getScene().setCursor(Cursor.HAND);
        } catch (Exception e) {

        }
            }

    @FXML
    void changeBack(MouseEvent event) {
        try{
            menuIcon.getScene().setCursor(Cursor.DEFAULT);
        } catch (Exception e) {

        }

    }

    @FXML
    public void initialize()
    {

        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            vBox.getScene().setCursor(Cursor.DEFAULT);
        });

        boolean deckPicked = false;
        fireBaseActions = FireBaseActions.init();
        currentUser = fireBaseActions.getCurrentUser();
        System.out.println(currentUser.getUsername());

        // Gets the deck names and checks to see if the deck name is null
        List<Deck> decks = currentUser.getDecks().stream()
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


    // Retrieves the anchorPane from homeButtonInterface
    @Override
    public AnchorPane getAnchorPane() { return anchorPane; }


   // Adds home button functionality
    @Override
    public void home(Event event) {
        homeButtonInterface.super.home(event);
    }


    @Override
    public void menuExit(Event event) {
        //homeButtonInterface.super.home(event);
        if (event.getSource() instanceof MenuItem)
            System.exit(0);
    }


    // Implements back button functionality
    public void backButton(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksMainMenu.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            anchorPane.getScene().setCursor(Cursor.DEFAULT);
            anchorPane.getScene().setRoot(mainMenuView);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // Method to return the selected deckId
    public String getCurrentSelectedDeckId()
    {
        return fireBaseActions.getCurrentDeck().getNameOfDeck();
    }


    // Method to set the deckId of the deck being selected
    public void setCurrentSelectedDeckId(int index)
    {
        Deck d = fireBaseActions.getCurrentUser().getDecks().get(index);
        fireBaseActions.setCurrentDeck(d);
    }


    // Sends user to Add screen
    @FXML
    private void addScreen()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksAdd.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            anchorPane.getScene().setCursor(Cursor.DEFAULT);
            anchorPane.getScene().setRoot(mainMenuView);

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // Sends user to Delete screen
    @FXML
    private void deleteScreen()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksDelete.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            anchorPane.getScene().setCursor(Cursor.DEFAULT);
            anchorPane.getScene().setRoot(mainMenuView);

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // Sends user to Edit screen
    @FXML
    private void editScreen()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksEdit.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            anchorPane.getScene().setCursor(Cursor.DEFAULT);
            anchorPane.getScene().setRoot(mainMenuView);

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
