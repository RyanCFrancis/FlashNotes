package org.flashnotes.flashnotes.View;

import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;

import java.io.IOException;
import java.util.Optional;

public class StudyScreenController implements homeButtonInterface {

    @FXML
    private MenuItem exit;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Label FlashNotes;

    @FXML
    private Button MatchingGameButton;

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    private Button backButton;


    @FXML
    private Label home;

    @FXML
    private Button leftButton;

    @FXML
    private Button menuIcon;

    @FXML
    private VBox vBox;

    User currentUser;

    boolean isFront;

    @FXML
    Card currentCard;

    @FXML
    Label card;

    @FXML
            Label index;

    @FXML
            Label title;

    int currentCardIndex;

    Deck currentDeck;

    FireBaseActions fireBaseActions;

    RotateTransition flipTransition;

    PauseTransition swapTextTransition;

    TranslateTransition jumpTransition;

    ParallelTransition cardFlipAnimator;


    @Override
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    @Override
    public void home(Event event) {
        homeButtonInterface.super.home(event);
    }

    @Override
    public void menuExit(Event event) {
        homeButtonInterface.super.menuExit(event);
    }


    @FXML
    public void initialize() {
        fireBaseActions = FireBaseActions.init();
//        System.out.println("Printing from study screen init");
        currentUser = fireBaseActions.getCurrentUser();
//        System.out.println(currentUser);
        currentDeck = fireBaseActions.getCurrentDeck();
//        System.out.println(currentDeck);
//        System.out.println(currentUser.getUsername());
        currentCardIndex = 1;
//        System.out.println(currentUser);
        isFront = true;
//        currentDeck = currentUser.getDecks().get(0);
//        currentDeck.getCards().add(new Card("Objects","I dont know ask the professor"));
//        currentDeck.getCards().add(new Card("random","not random"));
//        currentDeck.getCards().add(new Card("dang","dong"));
//        System.out.println(currentDeck.getCards().size());

        currentCard = currentDeck.getCards().get(0);
        index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        card.setText(currentCard.getFront());
        title.setText(currentDeck.getNameOfDeck());
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            title.getScene().setCursor(Cursor.DEFAULT);
        });



    }

    @FXML
    void changeToHand(MouseEvent event) {
        try {
            title.getScene().setCursor(Cursor.HAND);
        } catch (Exception e) {

        }
        }

    @FXML
    void changeBack(MouseEvent event) {
        try {
            title.getScene().setCursor(Cursor.DEFAULT);
        }catch (Exception e) {

        }
        }

    @FXML
    public void previousCard(){
        if(!(currentCardIndex == 1)){
            currentCardIndex--;
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText(currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        }else{
            currentCardIndex = currentDeck.getCards().size();
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText(currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");


        }

    }

    @FXML public void nextCard(){
        if(!(currentCardIndex == currentDeck.getCards().size())){
            currentCardIndex++;
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText(currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        }else{
            currentCardIndex = 1;
            currentCard = currentDeck.getCards().get(0);
            isFront = true;
            card.setText(currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");

        }
    }

    @FXML
   public void switchSide(){
        cardFlipAnimator = new ParallelTransition(flipCard(),cardJump(),changeText());
        System.out.println(isFront);

        cardFlipAnimator.play();
        isFront = !isFront;

        if(isFront){
            isFront = false;
            card.setText(currentCard.getBack());
        }else{
            isFront = true;
            card.setText(currentCard.getFront());
        }
    }

    private TranslateTransition cardJump(){
        TranslateTransition move = new TranslateTransition(Duration.millis(200),card);
        move.setByY(-20);
        move.setAutoReverse(true);
        move.setCycleCount(2);
        return move;
    }

    private RotateTransition flipCard(){
        RotateTransition rotate = new RotateTransition(Duration.millis(400), card);
        rotate.setAxis(Rotate.Y_AXIS);
        if(isFront){
            rotate.setFromAngle(0);
            rotate.setToAngle(180);
            rotate.setFromAngle(180);
            rotate.setToAngle(0);
        } else {
            rotate.setFromAngle(0);
            rotate.setToAngle(180);
            rotate.setFromAngle(180);
            rotate.setToAngle(0);
        }
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(1);

        return rotate;
    }


    private PauseTransition changeText(){
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(200));
        if(isFront){
            pauseTransition.setOnFinished( e -> {
                isFront = false;
               card.setText(currentCard.getBack());
            });
        } else {
            pauseTransition.setOnFinished( e -> {
                isFront = true;
                card.setText(currentCard.getFront());
           });
        }
        return pauseTransition;
    }

    @FXML
    private void goToGame() throws IOException {
        if (currentDeck.getCards().size() < 5){


            int difference = (5-currentDeck.getCards().size());
            String content = "Do you want to add more Cards? or go back to Studying?" +
                    "\nYou need to add "+difference+" more cards to play";
            if (difference == 1){
                content = "Do you want to add more Cards? or go back to Studying?" +
                        "\nYou need to add one more card to play";
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("You do not have enough Cards to play!");
            alert.setContentText(content);

            ButtonType buttonStudy = new ButtonType("Study");
            ButtonType buttonAdd = new ButtonType("Add More");
            ButtonType buttonCancel = new ButtonType("Home", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll( buttonAdd,buttonStudy,buttonCancel);
            Optional<ButtonType> ButtonResult = alert.showAndWait();

            if (ButtonResult.get() == buttonStudy) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/StudyScreen.fxml"));
                anchorPane.getScene().setRoot(fxmlLoader.load());
            } else if (ButtonResult.get() == buttonAdd) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MakeCard.fxml"));
                anchorPane.getScene().setRoot(fxmlLoader.load());
            } else {
                //user goes home
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MainMenu.fxml"));
                anchorPane.getScene().setRoot(fxmlLoader.load());
            }
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/flashnotes/flashnotes/MatchingScreen.fxml"));
        anchorPane.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    private void shareDeck(){
        TextInputDialog dialog = new TextInputDialog("@gmail.com");
        dialog.setTitle(null);
        dialog.setHeaderText("Sharing "+fireBaseActions.getCurrentDeck().getNameOfDeck()+" Deck");
        dialog.setContentText("Please enter their email");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                fireBaseActions.shareToUser(dialog.getResult().toLowerCase()
                        ,fireBaseActions.getCurrentDeck().getId());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Deck created successfully");
            alert.showAndWait();
        }
    }
}
