package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ShareScreenController {

    @FXML
    private Button checkButton;

    @FXML
    private TextField email;

    @FXML
    private Button returnButton;

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/NewMainMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (returnButton.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void checkForUser(ActionEvent event) {
        if(FireBaseActions.init().checkForUser(email.getText())) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("success");
            alert.setHeaderText(null);
            alert.setContentText("User with email " + email.getText()+ " found. Do you want to continue to share deck: " + FireBaseActions.init().getCurrentDeck().getNameOfDeck());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    FireBaseActions.init().shareToUser(email.getText().toLowerCase(), FireBaseActions.init().getCurrentDeck().getId());
                }catch (RuntimeException e){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    return;
                }
               Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
               alert2.setTitle("success");
               alert2.setHeaderText(null);
               alert2.setContentText("User with email " + email.getText()+ " has been shared.");
               alert2.showAndWait();
                try {
                    Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/NewMainMenu.fxml"));
                    Scene scene = new Scene(root, 800, 600);
                    Stage window = (Stage) (returnButton.getScene().getWindow());
                    window. setScene(scene);
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
                alert.close();

            }
        }

    }

}
