package pahail.the15game.core;

import com.sun.javafx.geom.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pahail.the15game.Const;
import pahail.the15game.Tile;
import pahail.the15game.core.FinalScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic{

    private Tile[] tiles = new Tile[Const.NUMBERTILE];
    private FinalScreen fs = new FinalScreen();


    public Parent newGame() {
        List<Integer> permutation = new ArrayList<>(Const.NUMBERTILE);
        for(int i = 0; i < Const.NUMBERTILE; i++) {
            permutation.add(i,i);
        }
        permutation.set(14,15);
        permutation.set(15,14);
        //Collections.shuffle(permutation);
        while(!doesSolutionExist(permutation)) {
            Collections.shuffle(permutation);
        }

        for(int i = 0; i < Const.NUMBERTILE; i++) {
            tiles[i] = new Tile(i, Const.GRID.getGrid()[(int) permutation.get(i)], (i+1)+".jpg" );
        }

        ImageView[] images = new ImageView[Const.NUMBERTILE];
        for(int i = 0; i < Const.NUMBERTILE; i++) {
            images[i] = tiles[i].getTexture();
            tiles[i].getTexture().setFitHeight(Const.SIZE * 1.95);
            tiles[i].getTexture().setFitWidth(Const.SIZE * 1.95);
            tiles[i].refreshImagePosition();
        }

        move();

        return new Pane(images);

    }

    //проверяем, что решение существует
    private boolean doesSolutionExist(List permutation) {
        int count = 0;
        boolean result = false;
        for(int i = 0; i < Const.NUMBERTILE - 1; i++) {
            int k = 0;
            for(int j = i + 1; j < Const.NUMBERTILE - 1; j++) {
                if( (int) permutation.get(i) > (int) permutation.get(j)) {
                    k++;
                }
            }
            count += k;
        }
        if( (count + (int) permutation.get(Const.NUMBERTILE - 1) / 4) % 2 == 1) {
            result = true;
        }
        return  result;
    }

    //возвращает номер плитки с указанной координатой
    private int isZero(Point2D p) {
        for(int i = 0; i < Const.NUMBERTILE; i++) {
            if( p.distance(tiles[i].getPosition()) < Const.EPS ) {
                return i;
            }
        }
        return -1;
    }

    private void swapTiles(Tile a, Tile b) {
        Point2D tmp = a.getPosition();
        a.setPosition(b.getPosition() );
        b.setPosition(tmp);
        a.refreshImagePosition();
        b.refreshImagePosition();

    }

    private void move() {

        for (Tile tile : tiles) {
            tile.getTexture().setOnMouseClicked((MouseEvent e) -> {

                if (nextMove(tile, 1, 0)) {
                    if (isSolved()) {
                        stopMoving();
                        showFinalScreen();
                    }
                    return;
                }
                if (nextMove(tile, -1, 0)) {
                    if (isSolved()) {
                        stopMoving();
                        showFinalScreen();
                    }
                    return;
                }
                if (nextMove(tile, 0, 1)) {
                    if (isSolved()) {
                        stopMoving();
                        showFinalScreen();
                    }
                    return;
                }
                if (nextMove(tile, 0, -1)) {
                    if (isSolved()) {
                        stopMoving();
                        showFinalScreen();
                    }
                    return;
                }

            });

        }

    }

    private void stopMoving() {
        for (Tile tile : tiles) {
            tile.getTexture().setOnMouseClicked((MouseEvent e) -> {
                //Не разобрался как убрать событие из просллушиваемых
                //Так что, просто поставил заглушку
            });
        }
    }

    private void showFinalScreen() {
        fs.getFinalScreen().show();
    }

    //Не забудь упростить, а то страшно как-то
    private boolean nextMove(Tile tile, int dx, int dy) {
        int nextTile = isZero(new Point2D(tile.getPosition().x + 2 * dx * Const.SIZE, tile.getPosition().y + 2 * dy * Const.SIZE));

        if (nextTile != Const.NUMBERTILE - 1) {
            if(nextTile != -1) {
                if(nextMove(tiles[nextTile],dx,dy) ){
                    swapTiles(tile, tiles[Const.NUMBERTILE - 1]);
                    return true;
                } else
                {
                    return false;
                }
            }
        } else {
            swapTiles(tile, tiles[Const.NUMBERTILE - 1]);
            return true;
        }
        return false;
    }

    private boolean isSolved() {
        for(int i = 0; i < Const.NUMBERTILE; i++) {
            if(tiles[i].getPosition().distance(Const.GRID.getGrid()[i]) > Const.EPS) {
                return false;
            }
        }
        return true;
    }


}
