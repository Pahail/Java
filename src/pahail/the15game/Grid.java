package pahail.the15game;

import com.sun.javafx.geom.Point2D;

public class Grid {
    private int size = 4;

    public Point2D[] getGrid() {
        return grid;
    }

    private Point2D[] grid = null;

    Grid() {
        grid = new Point2D[size * size];
        for(int count = 0; count < size * size; count++) {
            grid[count] = new Point2D( (float)(count % size + 0.25) * 2 * Const.SIZE,  (float) (count / size + 0.25) * 2 * Const.SIZE);
        }
    }

}
