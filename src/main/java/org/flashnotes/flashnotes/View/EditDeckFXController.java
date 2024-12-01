package org.flashnotes.flashnotes.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class EditDeckFXController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField catTxt;
    private FireBaseActions actions;
    private Deck curDeck;

    @FXML
    private void initialize(){
        actions = FireBaseActions.init();
        nameTxt.setText(actions.getCurrentDeck().getNameOfDeck());
        catTxt.setText(actions.getCurrentDeck().getCategory());
        curDeck = actions.getCurrentDeck();
    }

    @FXML
    public void editButton(){
        String name = nameTxt.getText();
        String category = catTxt.getText();



        if(name.isEmpty() || category.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please Fill Out Both Fields Properly");
            alert.showAndWait();
            return;
        }

        curDeck.setNameOfDeck(name);
        curDeck.setCategory(category);

        try {
            actions.updateDeck();
        } catch (ExecutionException | InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Deck Edited successfully");
        alert.showAndWait();

        try {
            Deck newDeck = new Deck(actions.getCurrentUser().getUsername(),name,category);
            actions.setCurrentDeck(newDeck);
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/ViewDecksMainMenu.fxml"));
            anchorPane.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML

    public void goBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/ViewDecksMainMenu.fxml"));
        anchorPane.getScene().setRoot(fxmlLoader.load());
    }
}