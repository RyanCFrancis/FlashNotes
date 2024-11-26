package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.util.concurrent.ExecutionException;

public class AddDeckController {

    @FXML
    private TextField Category;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createDeckbutton;

    @FXML
    private TextField nameOfDeck;

    FireBaseActions actions = FireBaseActions.init();

    @FXML
    void createDeck(ActionEvent event) {
        if(nameOfDeck.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a deck name");
            alert.showAndWait();
            return;
        }

        try {
            actions.uploadDeck(nameOfDeck.getText(), Category.getText());
        } catch (ExecutionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }catch (InterruptedException e) {
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
        alert.setContentText("Deck created successfully");
        alert.showAndWait();

        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/NewMainMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (Category.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void returnBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/NewMainMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (Category.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
