package GUI;

import javafx.scene.layout.*;
import javafx.scene.image.Image;

import javafx.scene.control.Label;

import Casino.Player;

public class PlayerPane extends AnchorPane {
    private Avatar avatar;
    public PlayerCardsHBox cardsBox;
    private LabelWithLastAction lastActionLabel;
    private Label numberOfChipsLabel;

    public PlayerPane(Image avatarImage, Player player) {
        setPrefHeight(203);
        setPrefWidth(138);

        avatar = new Avatar(avatarImage);
        cardsBox = new PlayerCardsHBox(player.getHoleCards());
        lastActionLabel = new LabelWithLastAction();
        numberOfChipsLabel = new LabelWithCurrentChips();

        numberOfChipsLabel.setText(Integer.toString(player.numberOfChips));
        lastActionLabel.setText(player.lastAction != null ? player.lastAction.toString() : "");

        this.getChildren().add(avatar);
        this.getChildren().add(cardsBox);
        this.getChildren().add(lastActionLabel);
        this.getChildren().add(numberOfChipsLabel);
    }

    public void setActionLabelText(String text) {
        lastActionLabel.setText(text);
    }

    public void setNumberOfChipsLabelText(int number) {
        numberOfChipsLabel.setText(Integer.toString(number));
    }

    public void clearActionLabelText() {
        setActionLabelText("");
    }

}