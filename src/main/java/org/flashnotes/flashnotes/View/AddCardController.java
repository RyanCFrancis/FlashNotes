package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.util.concurrent.ExecutionException;

public class AddCardController {

    @FXML
    private TextArea back;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createDeckbutton;

    @FXML
    private TextArea front;

    Deck  currDeck = FireBaseActions.init().getCurrentDeck();

    @FXML
    void createCard(ActionEvent event) {
        if(back.getText().isEmpty() || front.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;

        }

        currDeck.getCards().add(new Card(front.getText(), back.getText()));
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Card created");
        alert.showAndWait();
        returnBack(null);

    }

    @FXML
    void returnBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/EditDeck.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (back.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
