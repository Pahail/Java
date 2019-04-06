package primitives;

import primitives.Point;
import utils.FileUtils;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PointSet {
    private ArrayList<Point> points;
    //Две константы-ограничителя для случайной генерации
    private static final int MAXSIZE = 100;
    private static final int MAXVALUE = 10;

    public int size() {
        return points.size();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public PointSet(int dimension, int size){
        if(size < 0) {
            throw new IllegalArgumentException();
        }
        if(dimension < 1 || dimension > 3) {
            throw new IllegalArgumentException();
        }
        points = new ArrayList<>();
        points.ensureCapacity(size);
        Random random = new Random();
        if(dimension == 1) {
            for(int i = 0; i < size; i++) {
                points.add(new Point(MAXVALUE * random.nextDouble(), 0.0, 0.0) );
            }
        }
        if(dimension == 2) {
            for(int i = 0; i < size; i++) {
                points.add(new Point(MAXVALUE * random.nextDouble(), MAXVALUE * random.nextDouble(), 0.0) );
            }
        }
        if(dimension == 3) {
            for(int i = 0; i < size; i++) {
                points.add(new Point(MAXVALUE * random.nextDouble(), MAXVALUE * random.nextDouble(), MAXVALUE * random.nextDouble()) );
            }
        }
    }

    public PointSet(String fileName) {
        List<String> file;
        points = new ArrayList<>();
        try {
            file = FileUtils.readLines(fileName);
        } catch (Exception ex) {
            return;
        }
        points.ensureCapacity(file.size());
        for(String s: file) {
            List<Double> point;
            try {
                point = Parser.parseLine(s);
            } catch (Exception ex) {
                return;
            }
            if(point.size() != 3) {
                System.err.println("Expected only 3 dimensions");
                return;
            }
            points.add(new Point(point.get(0), point.get(1), point.get(2)) );
        }
    }

    public PointSet(int dimension) {
        this(dimension, new Random().nextInt(MAXSIZE));
    }

    public void add(Point p) {
        if(p != null) {
            points.add(p);
        }
    }

    public Point get(int index) {
        if(index > size()) {
            throw new IllegalArgumentException();
        }
        return points.get(index);
    }

    public void PrintAll() {
        int count = 0;
        for (Point p: points) {
            System.out.printf("%d : X: %g , Y: %g , Z: %g \n", count, p.getX(), p.getY(), p.getZ());
            count++;
        }
    }
}


