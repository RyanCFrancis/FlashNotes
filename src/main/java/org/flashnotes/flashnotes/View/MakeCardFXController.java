package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;
import java.util.Optional;

public class MakeCardFXController implements  homeButtonInterface{


    @FXML
    private AnchorPane anchorPane;
    private FireBaseActions fbActions;
    @FXML
    private TextField frontText;
    @FXML
    private TextField backText;

    private Deck currDeck;


    @Override
    public AnchorPane getAnchorPane() {
        return this.anchorPane;
    }

    // Adds home button functionality
    @Override
    public void home(Event event) { homeButtonInterface.super.home(event); }


    @Override
    public void menuExit(Event event) { homeButtonInterface.super.menuExit(event); }
    //just for later implementation when event handlers are set up
    @FXML
    private void initialize() {
        fbActions = FireBaseActions.init();
        currDeck = fbActions.getCurrentDeck();
    }


    @FXML
    private void createButton() throws IOException {
        if(frontText.getText().isEmpty() || backText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        }

        currDeck.getCards().add(new Card(frontText.getText(), backText.getText()));

        try {
            FireBaseActions.init().updateDeck();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        frontText.clear();
        backText.clear();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("The Card was added!!");
        alert.setContentText("Do you want to add more Cards? Study? or Play?");

        ButtonType buttonAdd = new ButtonType("Add More");
        ButtonType buttonStudy = new ButtonType("Study");
        ButtonType buttonPlay = new ButtonType("Play");
        ButtonType buttonCancel = new ButtonType("Home", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonAdd, buttonStudy, buttonPlay,buttonCancel);
        Optional<ButtonType> ButtonResult = alert.showAndWait();

        if (ButtonResult.get() == buttonAdd){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/org/flashnotes/flashnotes/MakeCard.fxml"));
            anchorPane.getScene().setRoot(fxmlLoader.load());
        } else if (ButtonResult.get() == buttonStudy) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/org/flashnotes/flashnotes/StudyScreen.fxml"));
            anchorPane.getScene().setRoot(fxmlLoader.load());
        } else if (ButtonResult.get() == buttonPlay) {

            if (fbActions.getCurrentDeck().getCards().size() < 5){
                int difference = (5-fbActions.getCurrentDeck().getCards().size());
                String content = "Do you want to add more Cards? or go back to Studying?" +
                        "\nYou need to add "+difference+" more cards to play";
                if (difference == 1){
                    content = "Do you want to add more Cards? or go back to Studying?" +
                            "\nYou need to add one more card to play";
                }

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("You do not have enough Cards to play!");
                alert.setContentText(content);

                ButtonType buttonStudy2 = new ButtonType("Study");
                ButtonType buttonAdd2 = new ButtonType("Add More");
                ButtonType buttonCancel2 = new ButtonType("Home", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll( buttonAdd2,buttonStudy2,buttonCancel2);
                Optional<ButtonType> ButtonResult2 = alert.showAndWait();

                if (ButtonResult2.get() == buttonStudy2) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getResource("/org/flashnotes/flashnotes/StudyScreen.fxml"));
                    anchorPane.getScene().setRoot(fxmlLoader.load());
                } else if (ButtonResult2.get() == buttonAdd2) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getResource("/org/flashnotes/flashnotes/MakeCard.fxml"));
                    anchorPane.getScene().setRoot(fxmlLoader.load());
                } else {
                    //user goes home
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
                    anchorPane.getScene().setRoot(fxmlLoader.load());
                }
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/org/flashnotes/flashnotes/MatchingScreen.fxml"));
            anchorPane.getScene().setRoot(fxmlLoader.load());
        } else {
            //user goes home
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
            anchorPane.getScene().setRoot(fxmlLoader.load());
        }
    }

}
