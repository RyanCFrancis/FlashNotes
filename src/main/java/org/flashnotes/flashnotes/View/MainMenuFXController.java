package org.flashnotes.flashnotes.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;

public class MainMenuFXController {

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
    public void initialize() {
        actions = FireBaseActions.init();
        profilePicture.setImage(actions.getCurrentUser().getImg());
        username.setText(actions.getCurrentUser().getUsername());
    }


    @FXML
    public void goToViewDecks() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksMainMenu.fxml"));
        anchorPane.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    public void goCreateDeck() throws IOException {
        if (actions.getCurrentUser().getDecks().size() >= 6){
            System.exit(0); //TODO WARNING THAT NO MORE CAN BE MADE
        }



        //TODO GO TO CREATEDECK FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/NewDeck.fxml"));
        anchorPane.getScene().setRoot(fxmlLoader.load());
    }


    // Retrieves the anchorPane from homeButtonInterface
    @Override
    public AnchorPane getAnchorPane()
    {
        return anchorPane;
    }


    // Adds home button functionality
    @Override
    public void home(Event event) { homeButtonInterface.super.home(event); }


    @Override
    public void menuExit(Event event) { homeButtonInterface.super.menuExit(event); }

}

