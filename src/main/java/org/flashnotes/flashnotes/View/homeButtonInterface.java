package org.flashnotes.flashnotes.View;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


// Necessary to override in implementation
public interface homeButtonInterface {

    AnchorPane getAnchorPane();

// Adds functionality to the home label
    default void home(Event event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/NewMainMenu.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            Scene currentScene = ((Node)event.getSource()).getScene();
            currentScene.setRoot(mainMenuView);
            //Scene mainMenuScene = new Scene(mainMenuView, 800, 600);
            //getAnchorPane().getScene().setRoot(mainMenuView);
        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

// Adds functionality to the drop down menu to exit out of the application
    default void menuExit(Event event)
    {
        try
        {
            Platform.exit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
