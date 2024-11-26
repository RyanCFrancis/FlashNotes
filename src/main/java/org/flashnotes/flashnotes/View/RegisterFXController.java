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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFXController {

    @FXML
    private Button RegisterButton;

    @FXML
    private Label flashLabel2;

    @FXML
    private Label noAccountLabel2;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private ImageView profilePicImgView;

    @FXML
    private Label registerLabel;

    @FXML
    private Hyperlink signInHyperLink;

    @FXML
    private Hyperlink uploadImageHyperLink;

    @FXML
    private TextField usernameTxt;

    @FXML
    void onSignInHyperLinkClicked(ActionEvent event) {
        try {
            // Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/Login.fxml"));
            Scene loginScene = new Scene(loader.load());

            // New scene
            Stage currentStage = (Stage) signInHyperLink.getScene().getWindow();
            currentStage.setScene(loginScene);

            // Title for the login screen
            currentStage.setTitle("Login - FlashNotes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

