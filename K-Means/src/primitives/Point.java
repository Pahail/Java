package primitives;

import java.util.Random;

public class Point {
    private double x;
    private double y;
    private double z;

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    //Немного смещает точку от исходной в случайном направлении
    public Point shift() {
        Random random = new Random();
        double x = random.nextDouble() - 0.5;
        double y = random.nextDouble() - 0.5;
        return new Point(getX() + x, getY() + y );
    }

    //Хоть какой-то компаратор
    public int compareTo(Point p) {
        if(this.x != p.x) {
            return (this.x > p.x) ? 1 : -1;
        } else {
            if(this.y != p.y) {
                return (this.y > p.y)? 1 : -1;

            } else {
                return (this.z > p.z)? 1 : -1;
            }
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

}
