package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ViewDecksMainMenuFXController {


    @FXML
    private VBox Decks;

    @FXML
    private Text FlashNotes;

    @FXML
    private Button logoutButton;


    @FXML
    private Button addButton;

    @FXML
    private Label emptyDecks;

    @FXML
    private Text nameIntro;

    @FXML
    private ImageView profileIMG;

    FireBaseActions actions = FireBaseActions.init();

    User curr;
    @FXML
    public void initialize() {
        curr = actions.getCurrentUser();
        Decks.getChildren().clear();
        System.out.println(curr.getImg());
        profileIMG.setImage(new Image(curr.getImg().getUrl()));
        profileIMG.setFitWidth(200);
        profileIMG.setFitHeight(200);
        nameIntro.setText("Welcome " + curr.getUsername() + "!");
        if(curr.getDecks().size() == 0) {
            emptyDecks.setVisible(true);
        }
        else {
            for(Deck d: curr.getDecks()){
                Label nameOfDeck = new Label("Name Of Deck: " + d.getNameOfDeck() + "\nOwner of deck: " + d.getOwnerOfDeck());
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction((ActionEvent event) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Deck");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete this deck?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {
                        curr.getDecks().remove(d);
                        try {
                            actions.updateDeck();
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        initialize();
                    }else {
                        alert.close();

                    }

                });

                Button studyButton = new Button("Study");
                studyButton.setOnAction((ActionEvent event) -> {
                    actions.setCurrentDeck(d);
                    try {
                        Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/StudyScreen.fxml"));
                        Scene scene = new Scene(root, 800, 600);
                        Stage window = (Stage) (addButton.getScene().getWindow());
                        window. setScene(scene);
                        window.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Button editDeckButton = new Button("Edit Deck");
                editDeckButton.setOnAction((ActionEvent event) -> {
                    actions.setCurrentDeck(d);
                    try {
                        Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/EditDeck.fxml"));
                        Scene scene = new Scene(root, 800, 600);
                        Stage window = (Stage) (addButton.getScene().getWindow());
                        window. setScene(scene);
                        window.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                HBox buttons = new HBox(new Label(d.getCards().size()+"/10 cards"),deleteButton, studyButton,editDeckButton);
                buttons.setSpacing(10);
                HBox selection = new HBox(nameOfDeck, buttons);
                selection.setStyle("-fx-background-color: white;");
                selection.setSpacing(200);
                        Decks.getChildren().add(selection);

            }
        }
    }

    @FXML
    void addDeck(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/NewAddDeck.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage window = (Stage) (FlashNotes.getScene().getWindow());
            window. setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            FireBaseActions.init().logout();
            try {
                Parent root = FXMLLoader.load(MainRunner.class.getResource("/org/flashnotes/flashnotes/login.fxml"));
                Scene scene = new Scene(root, 800, 600);
                Stage window = (Stage) (FlashNotes.getScene().getWindow());
                window. setScene(scene);
                window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            alert.close();
        }
    }

}
