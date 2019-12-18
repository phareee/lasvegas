package GUI;

import java.util.List;

import Casino.Card;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PlayerCardsHBox extends HBox {

    private static final int SPACING = 5;

    public PlayerCardsHBox(List<Card> cards) {
        setSpacing(SPACING);
        setLayoutX(80.0);
        setLayoutY(139.0);
        setPrefHeight(44.0);
        setPrefWidth(89.0);

        setCards(cards);
    }

    public void setCards(List<Card> cards) {
        getChildren().clear();
        for (Card card : cards) {
            ImageView cardView = new CardImageView(card, 80);
            getChildren().add(cardView);
        }
    }
}