package org.flashnotes.flashnotes.View;

import com.google.firebase.auth.FirebaseAuthException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.File;
import java.io.IOException;


//MainRunner.class.getResource("/org/flashnotes/flashnotes/SplashScreen.fxml")
public class MainRunner extends Application {

    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException, FirebaseAuthException {

        FireBaseActions.init();

        showMainApp(stage);
//        Image splashImage = new Image(getClass().getResource("/org/flashnotes/flashnotes/Images/Cover.jpg").toExternalForm());
//
//        ImageView splashImageView = new ImageView(splashImage);
//
//        splashImageView.setFitWidth(800);
//        splashImageView.setFitHeight(600);
//
//        StackPane root = new StackPane(splashImageView);
//        scene = new Scene(root, 800, 600); // Set the desired width and height
//
//
//        stage.setScene(scene);
//        stage.show();
//
//
//        // Add a fade-in and fade-out transition for splash screen
//        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), splashImageView);
//        fadeIn.setFromValue(0);
//        fadeIn.setToValue(1);
//
//        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), splashImageView);
//        fadeOut.setFromValue(1);
//        fadeOut.setToValue(0);
//        fadeOut.setDelay(Duration.seconds(2)); // Show the splash for 2 seconds before fading out
//
//        // Chain fade-in and fade-out and load the main window after
//        fadeIn.setOnFinished(e -> fadeOut.play());
//        fadeOut.setOnFinished(e -> {
//            stage.close();
//            showMainApp(stage);
//        });
//
//        fadeIn.play();

    }



    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainRunner.class.getResource(fxml ));
        return fxmlLoader.load();
    }

    private void showMainApp(Stage primaryStage) {
        // Set up the main application window
        try {
            scene = new Scene(loadFXML("/org/flashnotes/flashnotes/Login.fxml"),800,600);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}