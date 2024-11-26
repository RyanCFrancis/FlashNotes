package org.flashnotes.flashnotes.View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewDecksMainMenuFXController {

    @FXML
    private Label AddLabel;

    @FXML
    private Label DeckFiveLabel;

    @FXML
    private Label DeckFourLabel;

    @FXML
    private Label DeckOneLabel;

    @FXML
    private Label DeckSixLabel;

    @FXML
    private Label DeckThreeLabel;

    @FXML
    private Label DeckTwoLabel;

    @FXML
    private Label DeleteLabel;

    @FXML
    private Text FlashNotes;

    @FXML
    private Rectangle addLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label editLabel;

    @FXML
    private Text home;

    @FXML
    private ImageView menuIcon;

    @FXML
    private VBox vBox;



    public void initialize(String actionType)
    {
        if (actionType.equals("add"))
        {
            addScreen();
        }
        else if (actionType.equals("edit"))
        {
            editScreen();
        }
        else if (actionType.equals("delete"))
        {
            deleteScreen();
        }
    }


    // Calls loadScreen when user clicks add button
    @FXML
    public void addCard(Event event)
    {
        loadScreen("ViewDecksAdd.fxml");
    }


    // Calls loadscreen when user clicks delete button
    @FXML
    public void deleteCard(Event event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewDecksDelete.fxml"));
    }


    // Calls loadscreen when user clicks edit button
    @FXML
    public void editCard(Event event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewDecksEdit.fxml"));
    }


    // Method that loads an FXML file, creates its view, and sets it as the new content of the AnchorPane
    private void loadScreen(String screen)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(screen));
            Parent newView = fxmlLoader.load();

            anchorPane.getChildren().add(newView);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    // Setup logic for add screen
    @FXML
    private void addScreen()
    {
        System.out.println("Add Screen intialized...");
    }


    // Setup logic for the delete screen
    @FXML
    private void deleteScreen()
    {
        System.out.println("Delete Screen intialized...");
    }


    // Setup logic for the edit screen
    @FXML
    private void editScreen()
    {
        System.out.println("Edit Screen intialized...");
    }
}
