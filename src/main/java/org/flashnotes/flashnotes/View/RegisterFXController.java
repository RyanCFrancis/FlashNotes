package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.File;

public class RegisterFXController {

    private Alert alert;

    @FXML private Button RegisterButton;
    @FXML private Label flashLabel2;
    @FXML private Label noAccountLabel2;
    @FXML private PasswordField passwordTxt;
    @FXML private PasswordField confirmPasswordTxt;
    @FXML private TextField emailTxt;
    @FXML private ImageView profilePicImgView;
    @FXML private Label registerLabel;
    @FXML private Hyperlink signInHyperLink;
    @FXML private Hyperlink uploadImageHyperLink;
    @FXML private TextField usernameTxt;
    @FXML private ProgressIndicator progressIndicator;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB

    private File profileImageFile;

    @FXML
    public void initialize() {
        setupInitialState();
    }

    @FXML
    void changeToHand(MouseEvent event) {
        try {
            RegisterButton.getScene().setCursor(Cursor.HAND);
        }catch (Exception e){

        }
        }

    @FXML
    void changeBack(MouseEvent event) {
        try {
            RegisterButton.getScene().setCursor(Cursor.DEFAULT);
        }catch (Exception e){

        }
        }

    private void setupInitialState() {
        progressIndicator.setVisible(false); // Initially hide the progress indicator
        setDefaultProfileImage(); // Set a default profile image
        RegisterButton.setDefaultButton(true);
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            RegisterButton.getScene().setCursor(Cursor.DEFAULT);
        });

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

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        if (profileImageFile == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image file");
            alert.showAndWait();
            return;
        }

        if (passwordTxt.getText().length() < 6 || usernameTxt.getText().length() < 6) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username and password need to be at least 6 characters");
            alert.showAndWait();
            return;
        }

        try {
            FireBaseActions.init().Register(usernameTxt.getText(), emailTxt.getText(), passwordTxt.getText(), profileImageFile);
        } catch (FirebaseAuthException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Successful register");
        alert.showAndWait();

        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/Login.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (usernameTxt.getScene().getWindow());
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
