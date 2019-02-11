package pahail.the15game;

import com.sun.javafx.geom.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Tile {
    private Point2D position;
    private ImageView texture;
    private int number;

    public Point2D getPosition() {
        return position;
    }

    public ImageView getTexture() {
        return texture;
    }

    public int getNumber() {
        return number;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Tile() {
    }

    public Tile(int tileNumber, Point2D position, String imageName) {
        this.number = tileNumber;
        this.position = position;

        Alert errorTexturesAlert = new Alert(Alert.AlertType.ERROR);
        errorTexturesAlert.setHeaderText("Error");
        errorTexturesAlert.setContentText("Textures weren't found");

        try(FileInputStream file = new FileInputStream(new File("src/pahail/the15game/images/im1/" + imageName))) {
            texture = new ImageView(new Image(file));
        } catch (IOException e) {
            System.out.printf("Текстуры не найдены\n");
            errorTexturesAlert.showAndWait();
            System.exit(-1);
        }
    }

    public void refreshImagePosition(){
        this.texture.setTranslateX(this.position.x);
        this.texture.setTranslateY(this.position.y);
    }

}
