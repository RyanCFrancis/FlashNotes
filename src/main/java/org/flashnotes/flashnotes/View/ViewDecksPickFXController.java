package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;

import java.io.IOException;
import java.util.List;


// There is currrently no FireStore or FireBase implementation yet which is why
// FireBase Actions is commented out
// All buttons have functionality, the home button is in the homeButtonInterface
//Three other controllers were created to manage the specific functions of the add, delete, and edit
// When chosen decks get assigned a unique Id which keeps track of it


public class ViewDecksPickFXController implements homeButtonInterface {

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

    //Used what you said about Peter's youtrack Code
    //Deck deck = firebaseActions.getuser().getdecks(index);
    //firebaseActions.setCurrentDeck(deck);
    @FXML
    public void loadDeckNames() {
        // Get the list of decks from the current user
        List<Deck> decks = fireBaseActions.getCurrentUser().getDecks();

        // Clear the VBox before adding new labels
        vBox.getChildren().clear();

        // Check if the decks list is not empty
        if (decks != null && !decks.isEmpty()) {
            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                String deckName = deck != null ? deck.getNameOfDeck() : null;

                // Skip invalid decks
                if (deck == null || deckName == null || deckName.isEmpty() || deckName.equals("New deck 1")) {
                    continue; // Skip this deck and don't add it to the UI
                }

                // Determine which label corresponds to which deck (DeckOneLabel, DeckTwoLabel, etc.)
                Label deckLabel = getDeckLabel(i); // Get the corresponding Label (DeckOneLabel, DeckTwoLabel, etc.)

                if (deckLabel != null) {
                    // Set the label text to the deck's name
                    deckLabel.setText(deckName);

                    // Set visibility to true, as we have a valid deck to display
                    deckLabel.setVisible(true);

                    // Set the click event for each deck label to load the deck when clicked
                    deckLabel.setOnMouseClicked(event -> {
                        // Set the selected deck as the current deck using Firebase actions
                        fireBaseActions.setCurrentDeck(deck);

                        // You can log the selected deck or perform other actions here
                        System.out.println("Selected Deck: " + deckLabel.getText());
                    });
                }
            }
        } else {
            // If there are no valid decks, hide all labels or display a no decks message
            hideAllDeckLabels();
        }
    }

    // Helper method to get the correct Label based on the index
    private Label getDeckLabel(int index) {
        switch (index) {
            case 0: return DeckOneLabel;
            case 1: return DeckTwoLabel;
            case 2: return DeckThreeLabel;
            case 3: return DeckFourLabel;
            case 4: return DeckFiveLabel;
            case 5: return DeckSixLabel;
            default: return null; // Return null if index is out of bounds
        }
    }

    // Helper method to hide all deck labels
    private void hideAllDeckLabels() {
        DeckOneLabel.setVisible(false);
        DeckTwoLabel.setVisible(false);
        DeckThreeLabel.setVisible(false);
        DeckFourLabel.setVisible(false);
        DeckFiveLabel.setVisible(false);
        DeckSixLabel.setVisible(false);
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

    //facing errors loading the study screen fxml page, tried debugging with a sout to see if it was being clicked which it was
    //Study Screen Error printed was

    // Error loading FXML:
    ///Users/cmaradiaga/IdeaProjects/FlashNotes/target/classes/org/flashnotes/flashnotes/StudyScreen.fxml

    //Conclusion: it is being clicked but won't transtion
    @FXML
    public void goToStudyScreen(MouseEvent mouseEvent) {
        System.out.println("being Clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/StudyScreen.fxml"));
            Parent studyScreen = fxmlLoader.load();

            // Create and show the new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(studyScreen));
            stage.show();

            // Close the current stage
            ((Stage) anchorPane.getScene().getWindow()).close();

        } catch (IOException e) {
            System.out.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

    }


}


