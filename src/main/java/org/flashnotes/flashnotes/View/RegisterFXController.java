package org.flashnotes.flashnotes.View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;


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
    private ImageView profilePicImgView;

    @FXML
    private Label registerLabel;

    @FXML
    private Hyperlink signInHyperLink;

    @FXML
    private Hyperlink uploadImageHyperLink;

    @FXML
    private TextField usernameTxt;

    private File profileImageFile;

    // Initialize FireBaseActions class
    private final FireBaseActions fba = FireBaseActions.init();

    private void OpenFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        Stage stage = (Stage) profilePicImgView.getScene().getWindow();
        profileImageFile = fileChooser.showOpenDialog(stage);
        if (profileImageFile != null) {
            profilePicImgView.setImage(new Image(profileImageFile.toURI().toString()));
        }
    }



}

