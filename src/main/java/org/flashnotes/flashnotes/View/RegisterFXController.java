package org.flashnotes.flashnotes.View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        setupInitialState();

        // Add listeners to input fields to validate form on change
        usernameTxt.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        emailTxt.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        passwordTxt.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        confirmPasswordTxt.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }


    private void setupInitialState() {
        RegisterButton.setDisable(true);
        progressIndicator.setVisible(false); // Initially hide the progress indicator
        setDefaultProfileImage(); // Set a default profile image
    }

    private void setDefaultProfileImage() {
        try {
            // Set the default image in case the user hasn't uploaded one
            Image defaultImage = new Image(getClass().getResourceAsStream("/Images/face_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24.png"));
            profilePicImgView.setImage(defaultImage);
        } catch (Exception e) {
            System.err.println("Could not load default profile image: " + e.getMessage());
        }
    }

    private boolean validateInput(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showErrorMessage("Missing Information", "Please fill in all fields.");
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            showErrorMessage("Weak Password", "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showErrorMessage("Password Mismatch", "Passwords do not match.");
            return false;
        }

        if (profileImageFile == null) {
            showErrorMessage("Profile Picture Required", "Please upload a profile picture.");
            return false;
        }

        return true;
    }

    private void validateForm() {
        boolean isValid = !usernameTxt.getText().trim().isEmpty()
                && !emailTxt.getText().trim().isEmpty()
                && !passwordTxt.getText().isEmpty()
                && !confirmPasswordTxt.getText().isEmpty()
                && passwordTxt.getText().length() >= MIN_PASSWORD_LENGTH
                && passwordTxt.getText().equals(confirmPasswordTxt.getText())
                && profileImageFile != null;

        RegisterButton.setDisable(!isValid); // Disable the register button if the form is not valid
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        System.out.println("Register button clicked");

        String username = usernameTxt.getText().trim();
        String email = emailTxt.getText().trim();
        String password = passwordTxt.getText().trim();
        String confirmPassword = confirmPasswordTxt.getText().trim();

        // Validate user input before proceeding
        if (!validateInput(username, email, password, confirmPassword)) {
            return;
        }

        try {
            progressIndicator.setVisible(true);  // Show the progress indicator during registration
            isRegistering = true;

            // Perform the registration operation (assuming this method is synchronous)
            fba.Register(username, email, password, profileImageFile);

            // If we reach here, registration was successful
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);  // Hide the progress indicator
                showSuccessMessage();  // Show the success message
                clearForm();  // Clear all fields
                goToLogin(event);  // Navigate to the login screen
            });

        } catch (FirebaseAuthException e) {
            // Handle errors specific to Firebase authentication
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);  // Hide the progress indicator
                showErrorMessage("Firebase Error", "Error registering user: " + e.getMessage());
            });
        } catch (Exception e) {
            // Handle any unexpected errors
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);  // Hide the progress indicator
                showErrorMessage("Registration Error", "An unexpected error occurred: " + e.getMessage());
            });
        } finally {
            // Ensure that the registration state is reset after the operation
            isRegistering = false;
        }
    }


    private void clearForm() {
        // Clear all the form fields
        usernameTxt.clear();
        emailTxt.clear();
        passwordTxt.clear();
        confirmPasswordTxt.clear();

        // Reset the profile image to the default
        profileImageFile = null;
        setDefaultProfileImage();

        // Optionally, disable the register button until the form is valid again
        RegisterButton.setDisable(true);
    }

    private void showSuccessMessage() {
        // Display a success message when registration is successful
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText("Welcome to Flash Notes!");
        alert.setContentText("You have successfully registered. You can now log in with your credentials.");
        alert.showAndWait();  // Show the alert and wait for the user to acknowledge it
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            // Navigate to the login page
            Parent loginPage = FXMLLoader.load(getClass().getResource("/org/flashnotes/flashnotes/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginPage));
        } catch (Exception e) {
            showErrorMessage("Navigation Error", "Could not navigate to login page.");
            e.printStackTrace();  // Print the exception for debugging
        }
    }


    @FXML
    private void openFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath()); // Debug output
            if (selectedFile.length() > MAX_IMAGE_SIZE) {
                showErrorMessage("File Too Large", "Please select a file smaller than 5MB.");
                return;
            }

            // Set the selected image to the profile image view
            profileImageFile = selectedFile;
            profilePicImgView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }
}
