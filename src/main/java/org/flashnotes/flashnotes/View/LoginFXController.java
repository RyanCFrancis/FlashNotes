package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    void onRegisterHereLinkClicked(ActionEvent event) {
        try {
            // Register.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/Register.fxml"));
            Scene registerScene = new Scene(loader.load());

            // New scene
            Stage currentStage = (Stage) registerHereLink.getScene().getWindow();
            currentStage.setScene(registerScene);

            // Title for the Register screen
            currentStage.setTitle("Register - FlashNotes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
