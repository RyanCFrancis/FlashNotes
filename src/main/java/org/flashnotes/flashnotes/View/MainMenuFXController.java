package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;

public class MainMenuFXController implements homeButtonInterface {

    @FXML
    private Text FlashNotes;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label createDeckLabel;

    @FXML
    private Rectangle createDeckRectangle;

    @FXML
    private Text home;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Text username;

    @FXML
    private ImageView menuIcon;

    @FXML
    private Label studyDeckLabel;

    @FXML
    private Rectangle studyDeckRectangle;

    @FXML
    private VBox vBox;

    @FXML
    private Label viewDeckLabel;

    @FXML
    private Rectangle viewDeckRectangle;


    private FireBaseActions actions;
    @FXML
    private void initialize() {
        actions = FireBaseActions.init();
        profilePicture.setImage(new Image(actions.getCurrentUser().getImg().getUrl()));
        username.setText(actions.getCurrentUser().getUsername());
    }


    @FXML
    public void goToViewDecks() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksMainMenu.fxml"));
        viewDeckLabel.getScene().setCursor(Cursor.DEFAULT);
        anchorPane.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    public void goViewDecksPick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksPick.fxml"));
        viewDeckLabel.getScene().setCursor(Cursor.DEFAULT);
        anchorPane.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    public void goCreateDeck() throws IOException {
        if (actions.getCurrentUser().getDecks().size() >= 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Too many decks!");
            alert.showAndWait();
            return;
        }
        //TODO GO TO CREATEDECK FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/NewDeck.fxml"));
        viewDeckLabel.getScene().setCursor(Cursor.DEFAULT);
        if(anchorPane.getScene() != null) {
            anchorPane.getScene().setRoot(fxmlLoader.load());
        }
    }


    // Retrieves the anchorPane from homeButtonInterface
    @Override
    public AnchorPane getAnchorPane()
    {
        return anchorPane;
    }

    @FXML
    void changeToHand(){
        try {
            username.getScene().setCursor(Cursor.HAND);
        } catch (Exception e) {

        }
    }

    @FXML
    void changeBack(){
        try {
            username.getScene().setCursor(Cursor.DEFAULT);
        } catch (Exception e) {

        }
    }


    // Adds home button functionality
    @Override
    public void home(Event event) { homeButtonInterface.super.home(event); }


    @Override
    public void menuExit(Event event) { homeButtonInterface.super.menuExit(event); }

}

