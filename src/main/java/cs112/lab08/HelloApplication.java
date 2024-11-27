package cs112.lab08;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class HelloApplication extends Application /*implements EventHandler<ActionEvent>*/ {

    //CONSTANTS

    //array of LoteriaCards to use for game:
    private static final LoteriaCard[] LOTERIA_CARDS = {
            new LoteriaCard("Las matematicas", "1.png", 1),
            new LoteriaCard("Las ciencias", "2.png", 2),
            new LoteriaCard("La Tecnología", "8.png", 8),
            new LoteriaCard("La ingeniería", "9.png", 9),
    };

    private int count = 0;
    private static LoteriaCard[] shuffledCards = shuffleLoteriaDeck();

    private static LoteriaCard[] shuffleLoteriaDeck(){
        //deep copies arrays
        LoteriaCard[] cards = new LoteriaCard[LOTERIA_CARDS.length];

        //deep copies cards
        for(int i = 0; i < cards.length; i++){
            cards[i] = new LoteriaCard(LOTERIA_CARDS[i]);
        }

        //shuffle
        for(int i = 0; i < 10* LOTERIA_CARDS.length; i++){
            int randNum1 = (int) (Math.random() * LOTERIA_CARDS.length);
            int randNum2 = (int) (Math.random() * LOTERIA_CARDS.length);

            //swaps the cards around
            LoteriaCard temp = cards[randNum1];
            cards[randNum1] = cards[randNum2];
            cards[randNum2] = temp;

        }

        return cards;
    }

    @Override
    public void start(Stage stage) throws IOException {
        LoteriaCard cardLogo = new LoteriaCard();
        //setup components
        Label titleLabel = new Label("Welcome to EChALE STEM Loteria!");
        Label messageLabel = new Label("Click the button below to randomly draw a card.");
        Button drawCardButton = new Button("Draw Random Card");
        ImageView cardImageView = new ImageView(cardLogo.getImage());
        ProgressBar gameProgressBar = new ProgressBar();

        //customize components
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        cardImageView.setFitWidth(300);
        cardImageView.setPreserveRatio(true);
        titleLabel.setFont(new Font(20.0));
        drawCardButton.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent actionEvent){
                        if(count == shuffledCards.length){
                            //end game
                            messageLabel.setText("GAME OVER! No more cards! Exit and restart program to play again.");
                            cardImageView.setImage(cardLogo.getImage());
                            drawCardButton.setDisable(true);
                            gameProgressBar.setStyle("-fx-accent: red"); //CSS COOOODE
                        } else {
                            //generate random card
                            LoteriaCard randomCard = shuffledCards[count];

                            //change image
                            cardImageView.setImage(randomCard.getImage());

                            //change message label to card name
                            messageLabel.setText(randomCard.getCardName());

                            count++;

                            //update progress bar
                            double fillRatio = ((double) count )/ shuffledCards.length;
                            gameProgressBar.setProgress(fillRatio);
                        }
                    }
                }
        );

        //add components
        VBox vbox = new VBox();
        vbox.getChildren().addAll(titleLabel, cardImageView, messageLabel, drawCardButton, gameProgressBar); //vertical order
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);

        //setup scene and show
        Scene scene = new Scene(vbox, 350, 500);
        stage.setScene(scene);
        stage.setTitle("EchALE STEM Loteria");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}