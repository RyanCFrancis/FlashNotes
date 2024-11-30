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

                // Identify the corresponding label and stackpane
                StackPane deckStackPane = (StackPane) vBox.getChildren().get(i);
                Label deckLabel = (Label) deckStackPane.getChildren().get(1); // Assuming Label is the second child of StackPane

                // Update the deck label text
                deckLabel.setText(deckName);

                // Set the StackPane's visibility to true
                deckStackPane.setVisible(true);

                // Add a click event listener to each StackPane/Label
                deckStackPane.setOnMouseClicked(event -> {
                    // Set the selected deck's ID to the controller
                    setCurrentSelectedDeckId(deck.getId());

                    // You can log it or update the UI accordingly
                    System.out.println("Selected Deck: " + deckLabel.getText());
                });
            }
        } else {
            // If there are no valid decks, hide all StackPanes or display a no decks message
            for (int i = 0; i < 6; i++) {
                StackPane deckStackPane = (StackPane) vBox.getChildren().get(i);
                deckStackPane.setVisible(false);
            }
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


