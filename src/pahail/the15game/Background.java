package pahail.the15game;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Background {
    ImageView image = null;

    public Parent getBackground() {

        Rectangle rect = new Rectangle(8 * Const.SIZE + 10, 8 * Const.SIZE + 10, Color.WHITE);
        rect.setTranslateX(0.5 * Const.SIZE - 6);
        rect.setTranslateY(0.5 * Const.SIZE -6 );
        return new Pane(rect);
    }

}
