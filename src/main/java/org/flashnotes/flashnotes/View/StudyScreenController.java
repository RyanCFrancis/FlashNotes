package org.flashnotes.flashnotes.View;

import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;
import org.flashnotes.flashnotes.Model.User;

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
        currentUser = fireBaseActions.getCurrentUser();
        currentDeck = fireBaseActions.getCurrentDeck();
        System.out.println(currentDeck.toString());
//        System.out.println(currentUser.getUsername());
        currentCardIndex = 1;
        System.out.println("here");
        System.out.println(currentUser);
        isFront = true;
//        currentDeck = currentUser.getDecks().get(0);
//        currentDeck.getCards().add(new Card("Objects","I dont know ask the professor"));
//        currentDeck.getCards().add(new Card("random","not random"));
//        currentDeck.getCards().add(new Card("dang","dong"));
//        System.out.println(currentDeck.getCards().size());
        currentCard = currentDeck.getCards().get(0);
        index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        card.setText("Front:\n" + currentCard.getFront());
        title.setText(currentDeck.getNameOfDeck());


   }

    @FXML
    public void previousCard(){
        if(!(currentCardIndex == 1)){
            currentCardIndex--;
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        }else{
            currentCardIndex = currentDeck.getCards().size();
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");


        }

    }

    @FXML public void nextCard(){
        if(!(currentCardIndex == currentDeck.getCards().size())){
            currentCardIndex++;
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        }else{
            currentCardIndex = 1;
            currentCard = currentDeck.getCards().get(0);
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
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
            card.setText("Back:\n" + currentCard.getBack());
        }else{
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
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
               card.setText("Back:\n" + currentCard.getBack());
            });
        } else {
            pauseTransition.setOnFinished( e -> {
                isFront = true;
                card.setText("Front:\n" + currentCard.getFront());
           });
        }
        return pauseTransition;
    }
}
