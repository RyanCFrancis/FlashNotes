package org.flashnotes.flashnotes.View;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import org.flashnotes.flashnotes.Model.FireBaseActions;

import java.io.IOException;


// Necessary to override in implementation
public interface homeButtonInterface {

    javafx.scene.layout.AnchorPane getAnchorPane();

// Adds functionality to the home label
    default void home(Event event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
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

    default void logout(Node event)
    {
        FireBaseActions.init().logout();
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/Login.fxml"));
            Parent mainMenuView = fxmlLoader.load();
            Scene currentScene = event.getScene();
            currentScene.setRoot(mainMenuView);
            //Scene mainMenuScene = new Scene(mainMenuView, 800, 600);
            //getAnchorPane().getScene().setRoot(mainMenuView);
        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


}
