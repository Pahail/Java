package kmeans.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import primitives.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CanvasForPoints extends Canvas {

    private static final int MAXHEIGHT = 600;
    private static final int MAXWIDTH = 600;
    private static final int POINTHEIGHT = 8;
    private static final int POINTWIDTH = 8;
    private static List<Color> colors;
    private static int colorNumber = 0;

    public CanvasForPoints() {
        super(MAXHEIGHT, MAXWIDTH);
        colors = new ArrayList<>();
        colors.add(Color.GOLD);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.BROWN);
        colors.add(Color.ORANGE);
        colors.add(Color.STEELBLUE);
        colors.add(Color.DARKSALMON);
        colors.add(Color.PINK);
        colors.add(Color.FLORALWHITE);
        colors.add(Color.SILVER);
        colors.add(Color.CRIMSON);
    }

    private static Color nextColor() {
        colorNumber++;
        return colors.get(colorNumber % colors.size());
    }

    public void drawPoints(List<Point> list) {
        if(list == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        for(Point p :  list) {
            getGraphicsContext2D().fillOval( posX(p.getX()), posY(p.getY()), POINTWIDTH, POINTHEIGHT );
        }
    }

    public void drawCenters(List<Point> list) {
        if(list == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        for(Point p :  list) {
            getGraphicsContext2D().setFill(Color.BLACK);
            getGraphicsContext2D().fillOval( posX(p.getX()) - 1, posY(p.getY()) - 1, POINTWIDTH + 2, POINTHEIGHT + 2 );
            getGraphicsContext2D().setFill(Color.RED);
            getGraphicsContext2D().fillOval( posX(p.getX()), posY(p.getY()), POINTWIDTH, POINTHEIGHT  );
        }
    }

    public void eraseCenters(List<Point> list) {
        if(list == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        for(Point p :  list) {
            getGraphicsContext2D().setFill(Color.WHITE);
            getGraphicsContext2D().fillOval( posX(p.getX()) - 2, posY(p.getY()) - 2, POINTWIDTH + 4, POINTHEIGHT + 4 );
        }
    }


    public void drawCenter(Point p, Color color) {
        if(p == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }

        getGraphicsContext2D().setFill(Color.BLACK);
        getGraphicsContext2D().fillOval( posX(p.getX()) - 1, posY(p.getY()) - 1, POINTWIDTH + 2, POINTHEIGHT + 2 );
        getGraphicsContext2D().setFill(color);
        getGraphicsContext2D().fillOval( posX(p.getX()), posY(p.getY()), POINTWIDTH, POINTHEIGHT  );
    }

    public void eraseCenter(Point p) {
        if(p == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        getGraphicsContext2D().setFill(Color.WHITE);
        getGraphicsContext2D().fillOval( posX(p.getX()) - 1, posY(p.getY()) - 1, POINTWIDTH + 2, POINTHEIGHT + 2 );
    }

    public void drawPoints(List<Point> list, Color color) {
        if(list == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        getGraphicsContext2D().setFill(color);
        for(Point p :  list) {
            getGraphicsContext2D().fillOval( posX(p.getX()), posY(p.getY()), POINTWIDTH, POINTHEIGHT );
        }
    }

    public void drawClusters(List<Point> centers, List<List<Point>> clusters) {
        if(centers == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        if(clusters == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        int i = 0;
        for(List<Point> list : clusters) {
            Color color = nextColor();
            drawCenter(centers.get(i), color);
            drawPoints(list, color);
            i++;
        }
    }

    public void drawClusters(List<List<Point>> clusters) {
        if(clusters == null) {
            throw new IllegalArgumentException("Excepted not null argument");
        }
        for(List<Point> list : clusters) {
            Color color = nextColor();
            drawPoints(list, color);
        }
    }



    public void initDraw() {
        try{
            Image arrows = new Image(new FileInputStream("src/resources/arrows.png"));
            getGraphicsContext2D().setFill(Color.WHITE);
            getGraphicsContext2D().fillRect(0,0, MAXWIDTH, MAXHEIGHT);
            getGraphicsContext2D().setFill(Color.BLUEVIOLET);
            getGraphicsContext2D().drawImage(arrows, 5, 5, 80, 80);
        } catch (FileNotFoundException e) {
            System.err.println("Image is not found");
        }
    }

    public int posX(double x) {
        return (int)(x * 50) - 4;
    }

    public int posY(double y) {
        return (int)(y * 50) - 4;
    }

    public double scaleX(double x) {
        return x / 50;
    }

    public double scaleY(double y) {
        return y / 50;
    }

    public boolean addPoint(List<Point> list, MouseEvent event) {
        return list.add(new Point(scaleX(event.getX()), scaleY(event.getY())));
    }

}
