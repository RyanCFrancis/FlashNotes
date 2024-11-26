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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.File;

/**
 * FXML Controller for handling the user registration logic.
 * Handles the registration form submission, image upload,
 * and navigation between the registration and login screens.
 *
 * Action methods linked to the FXML view include:
 * - handleRegister: Triggered when the user clicks the "Register" button.
 * - goToLogin: Triggered when the user clicks on the login hyperlink.
 * - openFileChooser: Allows the user to upload a profile picture.
 * - setUpInitialState: Initializes the view with default settings.
 * - validateForm: Validates user input before enabling the "Register" button.
 */
public class RegisterFXController {

    @FXML
    private Button RegisterButton;
    @FXML
    private HBox uploadBox;
    @FXML
    private Label flashLabel2;
    @FXML
    private Label noAccountLabel2;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField confirmPasswordTxt;
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
    private TextField emailTxt;
    @FXML
    private ProgressIndicator progressIndicator;

    Alert alert;

    File imageToUpload;

    @FXML
    void FindImg(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) registerLabel.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            System.out.println("file not null");
            imageToUpload = file;
            uploadBox.getChildren().addAll(new Label(" " + file.getName()));
            return;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image file");
            alert.showAndWait();

        }

    }

    @FXML
    void Register(ActionEvent event){
        if(imageToUpload == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image file");
            alert.showAndWait();
            return;
        }
        if(passwordTxt.getText().length() < 6 || usernameTxt.getText().length() < 6) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username and password need to be at least 6 characters");
            alert.showAndWait();
            return;
        }


        try {
            FireBaseActions.init().Register(usernameTxt.getText(), emailTxt.getText(), passwordTxt.getText(), imageToUpload);
        }catch (FirebaseAuthException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }catch (Exception e){
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
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToLogin(){
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/Login.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (usernameTxt.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
