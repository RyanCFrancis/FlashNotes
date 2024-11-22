package org.flashnotes.flashnotes.View;

import com.google.firebase.ErrorCode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.File;

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
    private PasswordField confirmPasswordTxt;
    @FXML
    private TextField emailTxt;
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
    private ProgressIndicator progressIndicator;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB

    private File profileImageFile;
    private final FireBaseActions fba = FireBaseActions.init();
    private boolean isRegistering = false;

    @FXML
    public void initialize() {

    }

    private void setupEventHandlers() {
        uploadImageHyperLink.setOnAction(event -> openFileChooser());
        RegisterButton.setOnAction(event -> handleRegister());
        signInHyperLink.setOnAction(event -> navigateToLogin());
    }
    private void setUpInitialState(){
        RegisterButton.setDisable(true);
        profilePicImgView.setVisible(false);


    }
    private void setupValidations() {
    }

    private void validateForm() {
    }

    private void navigateToLogin() {
    }

    private void handleRegister() {
    }


    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );

        Stage stage = (Stage) profilePicImgView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            if (selectedFile.length() > MAX_IMAGE_SIZE) {
                showErrorMessage("Image too Large", "Please select a smaller image ")

            }
        }
    }

    private void showErrorMessage(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
