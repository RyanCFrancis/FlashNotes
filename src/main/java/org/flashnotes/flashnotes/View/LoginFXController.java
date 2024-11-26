package org.flashnotes.flashnotes.View;

import com.google.firebase.auth.FirebaseAuthException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.util.EventObject;

public class LoginFXController {

    @FXML
    private Label flashLabel1;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Label noAccountLabel;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Hyperlink registerHereLink;

    @FXML
    private TextField usernameTxt;

    private final FireBaseActions fireBaseActions = FireBaseActions.init();

    Alert alert;



   @FXML
    private void login(ActionEvent event) {
       try{
           fireBaseActions.login(usernameTxt.getText().trim(),passwordTxt.getText().trim());
       } catch (FirebaseAuthException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
       } catch (Exception e){
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText(e.getMessage());
           alert.showAndWait();
           return;
       }

       if(fireBaseActions.getCurrentUser() == null){
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Email or password is incorrect");
           alert.showAndWait();
           return;
       }

       goToMainMenu();




   }

   @FXML
    private void goToRegister(ActionEvent event) {
       try {
           Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/Register.fxml"));
           Scene scene = new Scene(root, 800, 600);
           Stage window = (Stage) (usernameTxt.getScene().getWindow());
           window. setScene(scene);
           window.show();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

     private void goToMainMenu(){
        try {
             Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/NewMainMenu.fxml"));
             Scene scene = new Scene(root, 800, 600);
             Stage window = (Stage) (usernameTxt.getScene().getWindow());
             window. setScene(scene);
             window.show();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }



}
