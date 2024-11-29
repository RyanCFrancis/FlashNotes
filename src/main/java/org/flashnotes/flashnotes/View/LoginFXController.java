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
import javafx.stage.StageStyle;
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


    private  FireBaseActions fireBaseActions;

    @FXML private ProgressIndicator progressIndicator;


    // private final FireBaseActions fireBaseActions = FireBaseActions.init();


    //just for later implementation when event handlers are set up
    @FXML
    public void initialize() {

        fireBaseActions  = FireBaseActions.init();

        progressIndicator.setVisible(false);

        loginButton.setOnAction(event -> handleLogin(event));
        registerHereLink.setOnAction(event -> navigateToRegister(event));
        loginButton.setDefaultButton(true);
    }
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = usernameTxt.getText().trim();
        String password = passwordTxt.getText();
        boolean loginWorked = true;

        try {
            fireBaseActions.login(email, password);

        } catch (FirebaseAuthException e) {
            System.out.println("Firebase authentication error: " + e.getMessage());
            loginWorked = false;
        } catch (IllegalArgumentException e) {
            System.out.println("Login error: " + e.getMessage());
            loginWorked = false;
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.out.println("Unexpected error during login: " + e.getMessage());
            loginWorked = false;
        }
        if (loginWorked){navigateToMainMenu(event);}

    }
    //Must create event to naviagate menu will copy and paste from 311 classes and will fix when done
    private void navigateToMainMenu(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
//            window.initStyle(StageStyle.UNDECORATED); hide border and titlebar
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    @FXML
    private void navigateToRegister(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/flashnotes/flashnotes/Register.fxml"));
            Scene scene = new Scene(root, 900, 600);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
