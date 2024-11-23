package org.flashnotes.flashnotes.View;

import com.google.firebase.auth.FirebaseAuthException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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



    private void handleLogin(ActionEvent event) {
        String email = usernameTxt.getText().trim();
        String password = passwordTxt.getText();

        try {
            // Attempt login
            fireBaseActions.login(email, password);

            // If login successful, navigate to main menu
            navigateToMainMenu(event);
        } catch (FirebaseAuthException e) {
            // Error handling will be added later by another team member
            System.out.println("Login failed: " + e.getMessage());
        }
    }
    //Must create event to naviagate menu will copy and paste from 311 classes and will fix when done
    private void navigateToMainMenu(ActionEvent event){
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
