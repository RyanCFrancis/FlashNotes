package org.flashnotes.flashnotes.View;

import com.google.firebase.auth.FirebaseAuthException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.File;
import java.io.IOException;

public class MainRunner extends Application {
    @Override
    public void start(Stage stage) throws IOException, FirebaseAuthException {
        FireBaseActions.init();
        FXMLLoader fxmlLoader = new FXMLLoader(MainRunner.class.getResource("/org/flashnotes/flashnotes/Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}