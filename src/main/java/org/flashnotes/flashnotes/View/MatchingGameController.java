package org.flashnotes.flashnotes.View;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.flashnotes.flashnotes.Model.Card;
import org.flashnotes.flashnotes.Model.Deck;
import org.flashnotes.flashnotes.Model.FireBaseActions;

public class MatchingGameController implements homeButtonInterface {

    @FXML
    private Button AnswerOneButton;

    @FXML
    private Button AnswerTwoButton;

    @FXML
    private Text FlashNotes;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label currCard;

    @FXML
    private Text home;

    @FXML
    private ImageView menuIcon;

    @FXML
    private Button nextCard;

    @FXML
    private Label score;

    @FXML
    private Label title;

    @FXML
    private VBox vBox;

    private Deck deck;

    private Card card;

    private int currIndex;

    boolean AnswerOneIsCorrect = false;

    int currScore = 0;

    int numberOfAttempts = 0;

    FireBaseActions actions;


    // Retrieves the anchorPane from homeButtonInterface
    @Override
    public AnchorPane getAnchorPane()
    {
        return anchorPane;
    }


    // Adds home button functionality
    @Override
    public void home(Event event) { homeButtonInterface.super.home(event); }


    @Override
    public void menuExit(Event event) { homeButtonInterface.super.menuExit(event); }




    @FXML
    void initialize() {
        actions = FireBaseActions.init();

        // deck = a.getCurrentDeck();
//       InMemoryDatabase db = new InMemoryDatabase();
//       deck = db.getDeck("deck1");
        deck = actions.getCurrentDeck();
        score.setText("Score= " + currScore +"/" + numberOfAttempts);
        System.out.println(deck);
        title.setText(deck.getNameOfDeck());
//        deck.getCards().add(new Card("Object"," a fundamental building block that represents an instance of a class. It encapsulates both data (attributes) and behavior (methods) into a single unit. They model real-world entities or concepts and allow us to organize code around these entities."));
//        deck.getCards().add(new Card("Variables","a storage location in a computer's memory that holds a value. The value of a variable can change (or vary) during the execution of a program. They are essential for storing data and interacting with it throughout your program."));
//        deck.getCards().add(new Card("Method"," A block of code that performs a specific task or set of tasks. Are used to structure and organize code by grouping related statements together, making code reusable and easier to maintain."));
//        deck.getCards().add(new Card("Interface"," In Java is a reference type, similar to a class, that can contain only abstract methods (methods without a body), default methods (with a body), static methods, and constant variables (public, static, and final). It defines a contract that classes can implement. An interface is used to specify a set of behaviors (methods) that a class must provide."));
//        deck.getCards().add(new Card("Polymorphism","A fundamental concept in object-oriented programming (OOP), it allows one entity (such as a method or object) to take on multiple forms. In Java, this enables objects of different classes to be treated as objects of a common superclass. The actual method that gets invoked is determined at runtime, allowing the same method name to behave differently based on the object’s type."));
//        deck.getCards().add(new Card("Inheritance","Is one of the core concepts of object-oriented programming (OOP). It allows a new class (called the subclass or child class) to inherit the properties and behaviors (fields and methods) of an existing class (called the superclass or parent class). This helps promote code reusability and establishes a relationship between the parent and child classes."));
        double random  = Math.random();
        int firstIndex = (int)(random * deck.getCards().size());
        currIndex = firstIndex;
        card = deck.getCards().get(firstIndex);
        currCard.setText(card.getFront());
        while(true) {
             random  = Math.random();
            int nextIndex = (int)(random * deck.getCards().size());
            if(nextIndex != currIndex) {
                random = (int) (Math.random() * 100);
                if (random % 2 == 0) {
                    AnswerOneButton.setText(deck.getCards().get(nextIndex).getBack());
                    AnswerTwoButton.setText(card.getBack());
                    AnswerOneIsCorrect = false;

                } else {
                    AnswerTwoButton.setText(deck.getCards().get(nextIndex).getBack());
                    AnswerOneButton.setText(card.getBack());
                    AnswerOneIsCorrect = true;
                }
                break;
            }
        }

    }


   @FXML
   void checkOne(){
        if(AnswerOneIsCorrect) {
            currScore++;
            numberOfAttempts++;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Correct");
            alert.setHeaderText("Correct");
            alert.setContentText("You are correct!");
            alert.showAndWait();
            score.setText("Score= " + currScore +"/" + numberOfAttempts);
            goToNextCard(null);

        }else{
            numberOfAttempts++;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect");
            alert.setHeaderText("Incorrect");
            alert.setContentText("You are incorrect!");
            alert.showAndWait();
            score.setText("Score= " + currScore +"/" + numberOfAttempts);
            goToNextCard(null);
        }
   }

   @FXML
   void checkTwo(){
       if(!AnswerOneIsCorrect) {
           currScore++;
           numberOfAttempts++;
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Correct");
           alert.setHeaderText("Correct");
           alert.setContentText("You are correct!");
           alert.showAndWait();
           score.setText("Score= " + currScore +"/" + numberOfAttempts);
           goToNextCard(null);

       }else{
           numberOfAttempts++;
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Incorrect");
           alert.setHeaderText("Incorrect");
           alert.setContentText("You are incorrect!");
           alert.showAndWait();
           score.setText("Score= " + currScore +"/" + numberOfAttempts);
          goToNextCard(null);
       }
   }

//    @FXML
//    void goHome(MouseEvent event) {
//
//    }

    void nextCard() {
        while(true) {
            double random  = Math.random();
            int nextIndex = (int)(random * deck.getCards().size());
            if(nextIndex != currIndex) {
                currIndex = nextIndex;
                card = deck.getCards().get(nextIndex);
                currCard.setText(card.getFront());
                break;
            }
        }
        while(true) {
            double random  = Math.random();
            int nextIndex = (int)(random * deck.getCards().size());
            if(nextIndex != currIndex) {
                random = (int) (Math.random() * 100);
                if (random % 2 == 0) {
                    AnswerOneButton.setText(deck.getCards().get(nextIndex).getBack());
                    AnswerTwoButton.setText(card.getBack());
                    AnswerOneIsCorrect = false;

                } else {
                    AnswerTwoButton.setText(deck.getCards().get(nextIndex).getBack());
                    AnswerOneButton.setText(card.getBack());
                    AnswerOneIsCorrect = true;
                }
                break;
            }
        }

    }

    @FXML
    void goToNextCard(ActionEvent event) {
        if(event != null) {
            numberOfAttempts++;
            score.setText("Score= " + currScore +"/" + numberOfAttempts);
        }
        while(true) {
            double random  = Math.random();
            int nextIndex = (int)(random * deck.getCards().size());
            if(nextIndex != currIndex) {
                currIndex = nextIndex;
                card = deck.getCards().get(nextIndex);
                currCard.setText(card.getFront());
                break;
            }
        }
        while(true) {
            double random  = Math.random();
            int nextIndex = (int)(random * deck.getCards().size());
            if(nextIndex != currIndex) {
                random = (int) (Math.random() * 100);
                if (random % 2 == 0) {
                    AnswerOneButton.setText(deck.getCards().get(nextIndex).getBack());
                    AnswerTwoButton.setText(card.getBack());
                    AnswerOneIsCorrect = false;

                } else {
                    AnswerTwoButton.setText(deck.getCards().get(nextIndex).getBack());
                    AnswerOneButton.setText(card.getBack());
                    AnswerOneIsCorrect = true;
                }
                break;
            }
        }


    }

}
