package org.flashnotes.flashnotes.View;

import com.google.firebase.auth.FirebaseAuthException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.EventObject;

public class LoginFXController {

    private static final Logger log = LoggerFactory.getLogger(LoginFXController.class);
    @FXML
    private Label flashLabel1;

    @FXML
    private VBox main;

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
        loginButton.setOnAction(event -> handleLogin(event));
        registerHereLink.setOnAction(event -> navigateToRegister(event));
        loginButton.setDefaultButton(true);


    }
    @FXML
    private void handleLogin(ActionEvent event){
        main.getChildren().add(4,new Label("Loading..."));
        String email = usernameTxt.getText().trim();
        String password = passwordTxt.getText();
        boolean loginWorked = true;



        try {
            fireBaseActions.login(email, password);

        } catch (FirebaseAuthException e) {
            System.out.println("Firebase authentication error: " + e.getMessage());
            loginWorked = false;
            main.getChildren().remove(4);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Login error: " + e.getMessage());
            loginWorked = false;
            main.getChildren().remove(4);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.out.println("Unexpected error during login: " + e.getMessage());
            loginWorked = false;
            main.getChildren().remove(4);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }


        if (loginWorked){navigateToMainMenu(event);}
        main.getChildren().remove(4);


    }

    //Must create event to naviagate menu will copy and paste from 311 classes and will fix when done
    private void navigateToMainMenu(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setResizable(false);
//            window.initStyle(StageStyle.UNDECORATED); hide border and titlebar
            loginButton.getScene().setCursor(Cursor.DEFAULT);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    @FXML
    void changeToHand(MouseEvent event) {
        try{
            loginButton.getScene().setCursor(Cursor.HAND);
        }catch (Exception e) {

        }

    }

    @FXML
    void changeBack(MouseEvent event) {
        try{
            loginButton.getScene().setCursor(Cursor.DEFAULT);
        }catch (Exception e) {

        }

    }

    @FXML
    private void navigateToRegister(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/flashnotes/flashnotes/Register.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setResizable(false);
            window.setScene(scene);
            loginButton.getScene().setCursor(Cursor.DEFAULT);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
