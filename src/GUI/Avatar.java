package GUI;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Avatar extends ImageView {

    private static final double LAYOUT_Y = 26.0;
    private static final boolean PRESERVE_RATIO = true;
    private static final boolean PICK_ON_BOUNDS = true;
    private static final double LAYOUT_X = 3.0;
    private static final int HEIGHT = 109;
    private static final int WIDTH = 102;

    public Avatar(Image image) {
        this.fitHeightProperty().set(HEIGHT);
        this.fitWidthProperty().set(WIDTH);
        this.setImage(image);
        setLayoutX(LAYOUT_X);
        setLayoutY(LAYOUT_Y);
        setPickOnBounds(PICK_ON_BOUNDS);
        setPreserveRatio(PRESERVE_RATIO);
    }
}