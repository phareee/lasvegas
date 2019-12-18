package GUI;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LabelWithLastAction extends Label {
    public LabelWithLastAction() {
        setLayoutX(25.0);
        setLayoutY(1.0);
        setFont(new Font(18));
        setTextFill(Color.web("#ffffff"));
    }
}