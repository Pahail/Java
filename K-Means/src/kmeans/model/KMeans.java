package kmeans.model;

import primitives.Line;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {

    private List<Point> dataSet;
    private int numberOfClusters;
    private List<Point> centers;
    private List<List<Point>> clusters;

    public List<List<Point>> getClusters() {
        return clusters;
    }

    public void setDataSet(List<Point> dataSet) {
        this.dataSet = dataSet;
    }

    public void setNumberOfClusters(int numberOfClusters) {
        this.numberOfClusters = numberOfClusters;
    }

    public List<Point> getCenters() {
        return centers;
    }

    private Point averaging(Point a, Point b) {
        return new Point( (a.getX() + b.getX()) / 2.0, (a.getY() + b.getY()) / 2.0);
    }

    private Point centerOfWeight(List<Point> list) {
        double x = 0;
        double y = 0;
        for(Point p : list) {
            x += p.getX();
            y += p.getY();
        }
        return new Point(x / list.size(), y / list.size());
    }

    private Point initialRandomCenter(){
        if(dataSet == null || dataSet.size() == 0) {
            System.err.println("Should before init dataSet");
            throw new RuntimeException("DataSet should be initialed");
        }
        Random random = new Random();
        int i = random.nextInt(dataSet.size());
        int j = random.nextInt(dataSet.size());
        int k = random.nextInt(dataSet.size());
        return averaging(dataSet.get(i).shift(), averaging(dataSet.get(j), dataSet.get(k)));

    }

    private double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) );
    }

    private int squareSign(Point a, Point b, Point c) {
        return ( (a.getX() - c.getX()) * (b.getY() - c.getY()) - (a.getY() - c.getY()) * (b.getX() - c.getX())  ) > 0 ? 1 : -1;
    }



    public Line separatingLine(Point a, Point b) {
        Point c = averaging(a, b);
        double x = c.getX() - 1;
        double y = c.getY() - (a.getX() - c.getX()) / (c.getY() - a.getY());
        return new Line(c, new Point(x,y));
    }

    public void step() {
        int i = 0;
        clusters.clear();
        for (Point a : centers) {
            clusters.add(new ArrayList<>(dataSet) );
            for (Point b : centers) {
                if(a == b) {
                    continue;
                }
                Line separator = separatingLine(a, b);
                int sign = squareSign(a, separator.getA(), separator.getB());
                int size = clusters.get(i).size();
                List<Point> temp = new ArrayList<>(clusters.get(i));
                for (Point p : temp){
                    if( sign * squareSign(p, separator.getA(), separator.getB()) <= 0 ) {
                        clusters.get(i).remove(p);
                    }
                }
            }
            i++;
        }
    }

    public boolean moveCenters () {
        int size = centers.size();
        boolean isMoved = false;
        List<Point> centersCopy = new ArrayList<>(centers);
        centers = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            Point np = centerOfWeight(clusters.get(i));
            if(distance(np, centersCopy.get(i)) > 1E-6) {
                isMoved = true;
            }
            centers.add(np);
        }
        return isMoved;
    }

    public KMeans(List<Point> dataSet, int numberOfClusters){
        this.dataSet = dataSet;
        this.numberOfClusters = numberOfClusters;
        centers = new ArrayList<>(numberOfClusters);
        int size = dataSet.size();
        clusters = new ArrayList<>(numberOfClusters);
        for(int i = 0; i < numberOfClusters; i++) {
            centers.add(initialRandomCenter() );
        }
    }
}
