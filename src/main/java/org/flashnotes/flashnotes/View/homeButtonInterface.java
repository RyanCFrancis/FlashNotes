package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public interface homeButtonInterface {

    javafx.scene.layout.AnchorPane getAnchorPane();


    default void home(Event event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            Scene mainMenuScene = new Scene(mainMenuView, 800, 600);
            getAnchorPane().getScene().setRoot(mainMenuView);
        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
