package org.flashnotes.flashnotes.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Firebase.FireBaseActions;

import java.io.IOException;

public class MainRunner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FireBaseActions.init();
        FXMLLoader fxmlLoader = new FXMLLoader(MainRunner.class.getResource("/org/flashnotes/flashnotes/SplashScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}