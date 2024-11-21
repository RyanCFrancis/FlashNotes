package org.flashnotes.flashnotes.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyScreenController {

    @FXML
    private Label FlashNotes;

    @FXML
    private Button MatchingGameButton;

    @FXML
    private AnchorPane anchorPane;

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



    @FXML
    public void initialize() {
        InMemoryDatabase database = new InMemoryDatabase();
        currentUser = database.getUser("1");
        currentCardIndex = 1;
        System.out.println("here");
        System.out.println(currentUser);
        isFront = true;
        currentDeck = currentUser.getDecks().get(0);
        currentDeck.getCards().add(new Card("Objects","I dont know ask the professor"));
        currentDeck.getCards().add(new Card("random","not random"));
        currentDeck.getCards().add(new Card("dang","dong"));
        currentCard = currentUser.getDecks().get(0).getCards().get(0);
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
        }
    }

    @FXML public void nextCard(){
        if(!(currentCardIndex == currentDeck.getCards().size())){
            currentCardIndex++;
            currentCard = currentDeck.getCards().get(currentCardIndex-1);
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
            index.setText(currentCardIndex +"/" + currentDeck.getCards().size() + " cards");
        }
    }

    @FXML
    public void switchSide(){
        if(isFront){
            isFront = false;
            card.setText("Back:\n" + currentCard.getBack());
        }else{
            isFront = true;
            card.setText("Front:\n" + currentCard.getFront());
        }
    }


}
