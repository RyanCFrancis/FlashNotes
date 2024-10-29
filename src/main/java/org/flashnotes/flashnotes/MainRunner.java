package org.flashnotes.flashnotes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainRunner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainRunner.class.getResource("SplashScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600.0,388.0);
        stage.setTitle("Flash Notes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}