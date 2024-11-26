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
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.util.concurrent.ExecutionException;

public class EditCardController {

    @FXML
    private TextArea back;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createDeckbutton;

    @FXML
    private TextArea front;

    FireBaseActions a = FireBaseActions.init();

    @FXML
    void initialize() {
        front.setText(a.selectedCard.getFront());
        back.setText(a.selectedCard.getBack());
    }

    @FXML
    void editCard(ActionEvent event) {
        if(front.getText().isEmpty() || back.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please have something for both fields");
            alert.showAndWait();
            return;

        }

        a.selectedCard.setFront(front.getText());
        a.selectedCard.setBack(back.getText());
        try{
            a.updateDeck();
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
        alert.setContentText("Card updated successfully");
        alert.showAndWait();
        a.selectedCard = null;

        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/EditDeck.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (front.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void returnBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/EditDeck.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (front.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
