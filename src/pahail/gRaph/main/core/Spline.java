package pahail.gRaph.main.core;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class Spline {

    private int size;
    private double[] a;
    private double[] b;
    private double[] c;
    private double[] d;
    private List<Double> grid;

    public Spline (List<Double> x, List<Double> y) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Can't create spline");
        alert.setHeaderText(null);
        if(x.size() != y.size()) {
            alert.setContentText("Numbers of X's and Y's are not equal\n");
            System.err.println("Can't create spline");
            alert.showAndWait();
            throw new IllegalArgumentException();
        }
        if(x.size() <= 2) {
            alert.setContentText("Series have to contain more than 2 points\n");
            System.err.println("Can't create spline");
            alert.showAndWait();
            throw new IllegalArgumentException();
        }
        boolean isSorted = true;
        for(int i = 1; i < x.size(); i++ ) {
            if(x.get(i-1) > x.get(i)) {
                isSorted = false;
                break;
            }
        }
        if(!isSorted) {
            alert.setContentText("Expected sorted sequences of input data\n");
            System.err.println("Can't create spline");
            alert.showAndWait();
            throw  new IllegalArgumentException();
        }
        grid = x;
        size = x.size() - 1;
        a = new double[size + 2];
        b = new double[size + 2];
        c = new double[size + 2];
        d = new double[size + 2];
        calculateCoefficients(x, y);

    }

    private void calculateCoefficients (List<Double> x, List<Double> y) {
        double[] h = new double[size + 1];
        double[] l = new double[size + 1];
        double[] alpha = new double[size + 1];
        double[] beta = new double[size + 1];
        for(int i = 1; i <= size; i++) {
            h[i] = x.get(i) - x.get(i-1);
            l[i] = (y.get(i) - y.get(i-1) ) / h[i];
        }
        alpha[1] = - h[2] / (2 * (h[1] + h[2]));
        beta[1] = 3 * (l[2] - l[1] ) / (2 * (h[1] + h[2]));
        c[0] = c[size+1] = 0;
        for(int i = 3; i <= size; i++){
            alpha[i - 1] = -h[i] / (2*h[i-1] + 2*h[i] + h[i-1]*alpha[i-2] );
            beta[i - 1] = (3*l[i] - 3*l[i-1] - h[i-1]*beta[i-2]) / (2*h[i-1] + 2*h[i] + h[i-1]*alpha[i-2] );
        }

        for(int i = size + 1; i > 1; i--) {
            c[i-1] = alpha[i-1] * c[i] + beta[i-1];
        }
        for(int i = 1; i <= size; i++) {
            b[i] = l[i] + (2*c[i]*h[i] + h[i]*c[i-1]) / 3;
            d[i] = (c[i] - c[i-1])/ (3*h[i]);
            a[i] = y.get(i);
        }


    }

    public double getMaxX() {
        return grid.get(size);
    }

    public double getValue(double x) throws Exception {
        for(int i = 0; i < size; i++) {
            if(x >= grid.get(i) && x <= grid.get(i+1)) {
                return a[i+1] + b[i+1]*pow(x-grid.get(i+1),1) + c[i+1]*pow(x-grid.get(i+1),2) + d[i+1]*pow(x-grid.get(i+1),3);
            }
        }
        if(x > grid.get(size)) {
            return a[size] + b[size] * pow(x - grid.get(size), 1) + c[size] * pow(x - grid.get(size), 2) + d[size] * pow(x - grid.get(size), 3);
        }


        throw new IllegalArgumentException();
    }

    public double getDerivativeValue (double x) throws Exception {
        for(int i = 0; i < size; i++) {
            if(x >= grid.get(i) && x <= grid.get(i+1)) {
                return b[i+1] + 2 * c[i+1]*pow(x-grid.get(i+1),1) + 3 * d[i+1]*pow(x-grid.get(i+1),2);
            }
        }
        throw new IllegalArgumentException();
    }

    public void printCoef(int n) {
        System.out.println(a[n] + " + " + b[n] + " x " + c[n] + " x^2 + " + d[n] + "x^3" );
    }

    public static void main(String[] args) throws Exception {
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        x.add(1.0);
        x.add(2.0);
        x.add(3.0);
        x.add(4.0);
        x.add(5.0);
        y.add( 5.0 );
        y.add( 3.0 );
        y.add( 2.5 );
        y.add( 2.0 );
        y.add( 0.0 );
        System.out.println(x.size());
        Spline spline = new Spline(x,y);
        for(int i = 1; i < 5; i++) {
            spline.printCoef(i);
        }
    }

}
