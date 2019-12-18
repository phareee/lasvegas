package GUI;

import javafx.scene.image.ImageView;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.beans.value.ObservableValue;

import javafx.scene.layout.*;
import javafx.scene.image.Image;

public class BotPane extends Pane {
    private Label selectBotLabel;
    private ImageView botAvatar;

    public String BotName;
    public ChoiceBox<String> botChooser;
    public boolean botChoosen = false;
    public Image avatarImage;

    private final double DEFAULT_HEIGHT = 200.0;
    private final double DEFAULT_WIDTH = 200.0;

    private final double LABEL_DEFAULT_LAYOUT_X = 65.0;
    private final double LABEL_DEFAULT_LAYOUT_Y = 224.0;
    private final String LABEL_DEFAULT_TEXT = "Select Bot";

    private final double CHOICE_BOX_DEFAULT_LAYOUT_X = 25.0;
    private final double CHOICE_BOX_DEFAULT_LAYOUT_Y = 184.0;
    private final double CHOICE_BOX_DEFAULT_WIDTH = 150.0;

    private final double AVATAR_DEFAULT_HEIGHT = 130.0;
    private final double AVATAR_DEFAULT_WIDTH = 150.0;
    private final double AVATAR_DEFAULT_LAYOUT_X = 29.0;
    private final double AVATAR_DEFAULT_LAYOUT_Y = 28.0;

    final HashMap<String, String> botNameToAvatar = new HashMap<>();
    {
        botNameToAvatar.put("Bear", "bear.png");
        botNameToAvatar.put("Cat", "cat.png");
        botNameToAvatar.put("Dog", "dog.png");
        botNameToAvatar.put("Eagle", "eagle.png");
        botNameToAvatar.put("Elephant", "elephant.png");
        botNameToAvatar.put("Horse", "horse.png");
        botNameToAvatar.put("Lion", "lion.png");
        botNameToAvatar.put("Monkey", "monkey.png");
        botNameToAvatar.put("Panda", "panda.png");
        botNameToAvatar.put("Penguin", "penguin.png");
        botNameToAvatar.put("Rabbit", "rabbit.png");
        botNameToAvatar.put("Seal", "seal.png");
        botNameToAvatar.put("Tiger", "tiger.png");
        botNameToAvatar.put("Wolf", "wolf.png");
        botNameToAvatar.put("Zebra", "zebra.png");
    }

    public BotPane() {
        this.setPrefHeight(DEFAULT_HEIGHT);
        this.setPrefWidth(DEFAULT_WIDTH);

        initializeBotNameLabel();
        initializeBotChooser();
        initializeBotAvatar();
    }

    private void setImageAvatar(String name) {
        String url = botNameToAvatar.get(name);
        avatarImage = new Image(url);
        botAvatar.setImage(avatarImage);
    }

    private void initializeBotNameLabel() {
        selectBotLabel = new Label();
        selectBotLabel.setLayoutX(LABEL_DEFAULT_LAYOUT_X);
        selectBotLabel.setLayoutY(LABEL_DEFAULT_LAYOUT_Y);
        selectBotLabel.setText(LABEL_DEFAULT_TEXT);
        selectBotLabel.setStyle("-fx-font-size: 12pt; -fx-text-fill: white;");
        this.getChildren().add(selectBotLabel);
    }

    private void initializeBotChooser() {

        botChooser = new ChoiceBox<String>(FXCollections.observableArrayList(botNameToAvatar.keySet()));
        botChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                selectBotLabel.setVisible(false);
                setImageAvatar((String) botNameToAvatar.keySet().toArray()[newValue.intValue()]);
                BotName = (String) botNameToAvatar.keySet().toArray()[newValue.intValue()];
                botChoosen = true;
            }
        });

        botChooser.setLayoutX(CHOICE_BOX_DEFAULT_LAYOUT_X);
        botChooser.setLayoutY(CHOICE_BOX_DEFAULT_LAYOUT_Y);
        botChooser.setPrefWidth(CHOICE_BOX_DEFAULT_WIDTH);

        this.getChildren().add(botChooser);
    }

    private void initializeBotAvatar() {
        botAvatar = new ImageView();
        botAvatar.setFitHeight(AVATAR_DEFAULT_HEIGHT);
        botAvatar.setFitWidth(AVATAR_DEFAULT_WIDTH);
        botAvatar.setLayoutX(AVATAR_DEFAULT_LAYOUT_X);
        botAvatar.setLayoutY(AVATAR_DEFAULT_LAYOUT_Y);
        botAvatar.setPickOnBounds(true);
        botAvatar.setPreserveRatio(true);

        this.getChildren().add(botAvatar);
    }
}