package org.flashnotes.flashnotes.View;

import com.google.firebase.ErrorCode;
import javafx.application.Platform;
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
        boolean isValid = !usernameTxt.getText().trim().isEmpty()
                && !emailTxt.getText().trim().isEmpty()
                && !passwordTxt.getText().isEmpty()
                && !confirmPasswordTxt.getText().isEmpty()
                && passwordTxt.getText().length() >= MIN_PASSWORD_LENGTH
                && passwordTxt.getText().equals(confirmPasswordTxt.getText())
                && profileImageFile != null
                && !isRegistering;


        RegisterButton.setDisable(!isValid);
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
                showErrorMessage("Image too Large", "Please select a smaller image ");
            }
            try{
                Image image = new Image(selectedFile.toURI().toString());
                if(image.isError()){
                    throw new Exception("Failed to Load Image");
                }
                profilePicImgView.setImage(image);
                profileImageFile = selectedFile;

            }catch(Exception e){
                showErrorMessage("Failed to Load Image", "Please select a smaller image ");

            }
        }
    }
    private void setDefaultProfileImage(){
        try {
            Image defaultImage = new Image(getClass().getResourceAsStream(
                    "/Images/face_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24.png")); // Image that is in scenebuilder
            profilePicImgView.setImage(defaultImage);
        } catch (Exception e) {
            // Log the error and possibly show a message in the UI
            System.err.println("Could not load default profile image: " + e.getMessage());
        }
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("You can now sign in with your credentials.");
        alert.showAndWait();
    }


    private void clearForm() {
        usernameTxt.clear();
        emailTxt.clear();
        passwordTxt.clear();
        confirmPasswordTxt.clear();
        profileImageFile = null;
        setDefaultProfileImage();
        validateForm();
    }


    private void showErrorMessage(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void handleFirebaseError(FirebaseAuthException e) {
        String message;
        String errorMessage = e.getMessage().toLowerCase();


        if (errorMessage.contains("email already in use")) {
            message = "This email is already registered.";
        } else if (errorMessage.contains("invalid email")) {
            message = "The email address is not valid.";
        } else if (errorMessage.contains("weak password")) {
            message = "Please choose a stronger password.";
        } else {
            message = "Registration failed: " + e.getMessage();
        }

        Platform.runLater(() ->
                showErrorMessage("Registration Error", message));
    }


}
