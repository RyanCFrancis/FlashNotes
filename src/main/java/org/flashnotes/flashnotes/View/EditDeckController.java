package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.util.concurrent.ExecutionException;

public class EditDeckController {

   @FXML
   private Button cancelButton;

    @FXML
    private VBox cards;

    @FXML
    private Button addCardButton;

    @FXML
    private TextField category;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField nameOfDeck;

    Deck currentDeck;

    @FXML
    void initialize() {
        currentDeck = FireBaseActions.init().getCurrentDeck();
        cards.getChildren().clear();
        for(Card card : currentDeck.getCards()) {
            Label info = new Label("Front: " + card.getFront() + "\nBack: " + card.getBack());
            Button editButton = new Button("Edit");
            editButton.setOnAction(event -> {
                try {
                    Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/EditCardScreen.fxml"));
                    Scene scene = new Scene(root, 800, 600);
                    Stage window = (Stage) (addCardButton.getScene().getWindow());
                    window. setScene(scene);
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button deleteButton = new Button("Delete");
            HBox buttons = new HBox( editButton, deleteButton);
            HBox container = new HBox(info, buttons);
            container.setSpacing(200);
            cards.getChildren().add(container);

        }
    }


    @FXML
    void confirmNameOrCategoryChanges(ActionEvent event) {
        currentDeck.setNameOfDeck(nameOfDeck.getText());
        currentDeck.setCategory(category.getText());
        try {
            FireBaseActions.init().updateDeck();
        } catch (ExecutionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();

        }
    }

    @FXML
    void addCard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/AddCardScreen.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (addCardButton.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

