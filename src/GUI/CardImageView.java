package GUI;

import Casino.Card;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class CardImageView extends ImageView {

    private static final int DEFAULT_HEIGHT = 80;
    private static final double DEFAULT_OPACITY = 1.0;

    public final Card card;

    public CardImageView(Card c, int height, double opacity) {
        this.card = c;
        String url = c != null ? getCardFileName(c) : "backside.png";
        Image image = new Image(url);
        setProporties(height, opacity);
        setImage(image);
    }

    public CardImageView(Card c) {
        this(c, DEFAULT_HEIGHT, DEFAULT_OPACITY);
    }

    public CardImageView(Card c, int height) {
        this(c, height, DEFAULT_OPACITY);
    }

    public CardImageView(Card c, double opacity) {
        this(c, DEFAULT_HEIGHT, opacity);
    }

    private String getCardFileName(Card c) {

        int cardNumer = c.getRank().ordinal() + 2;
        String cardSuit = c.getSuit().toString().substring(0, 1).toUpperCase();
        return cardNumer + cardSuit + ".png";
    }

    private void setProporties(int height, double opacity) {
        setFitHeight(height);
        setPreserveRatio(true);
        setSmooth(true);
        setOpacity(opacity);
    }
}