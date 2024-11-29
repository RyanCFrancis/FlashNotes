package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;

import java.io.IOException;


// There is currently no FireStore or FireBase implementation yet which is why
// FireBase Actions is commented out
// All buttons have functionality, the home button is in the homeButtonInterface
//Three other controllers were created to manage the specific functions of the add, delete, and edit
// When chosen decks get assigned a unique Id which keeps track of it


public class ViewDecksMainMenuFXController implements homeButtonInterface {

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




    public void initialize()
    {
        fireBaseActions = FireBaseActions.init();
        currentUser = fireBaseActions.getCurrentUser();
        System.out.println(currentUser.getUsername());
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
            anchorPane.getScene().setRoot(mainMenuView);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // Method to return the selected deckId
    public String getCurrentSelectedDeckId()
    {
        return currentSelectedDeckId;
    }


    // Method to set the deckId of the deck being selected
    public void setCurrentSelectedDeckId(String deckId)
    {
        this.currentSelectedDeckId = deckId;
    }


    // Sends user to Add screen
    @FXML
    private void addScreen()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksAdd.fxml"));
            Parent mainMenuView = fxmlLoader.load();
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
            anchorPane.getScene().setRoot(mainMenuView);

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
