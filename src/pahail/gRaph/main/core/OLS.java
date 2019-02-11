package pahail.gRaph.main.core;

import com.sun.javafx.charts.Legend;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import java.util.ArrayList;
import java.util.List;


public class OLS {

    static public Series getOLS(int number) {
        // Y = coefficientA * X + coefficientB
        double coefficientA;
        double coefficientB;
        double sumFirst = 0;
        double sumSecond = 0;
        double sumMultiplication = 0;
        double sumFirstSquare = 0;
        ObservableList<XYChart.Data<Number, Number> > target = Management.Control.getSeriesList().get(number).getData();
        int size = target.size();
        for(int i = 0; i < size; i++) {
            sumFirst += target.get(i).getXValue().doubleValue();
            sumFirstSquare += Math.pow(target.get(i).getXValue().doubleValue(), 2);
            sumSecond += target.get(i).getYValue().doubleValue();
            sumMultiplication += target.get(i).getXValue().doubleValue() * target.get(i).getYValue().doubleValue();
        }
        coefficientA = (size * sumMultiplication - sumFirst * sumSecond) / (size * sumFirstSquare - Math.pow(sumFirst, 2));
        coefficientB = (sumSecond - coefficientA * sumFirst) / size;
        ChartStyleMaker customStyle = Management.Control.getStyleList().get(number);
        customStyle.setOlsCoefficientA(coefficientA);
        customStyle.setOlsCoefficientB(coefficientB);
        System.out.printf("%d %g %g\n",target.size(), coefficientA, coefficientB );

        List<Double> first = new ArrayList<>();
        List<Double> second = new ArrayList<>();
        double maxX = getMaxValue() * 1.05;
        double minX = getMinValue() * 1.05;
        first.add(minX);
        first.add(maxX);
        second.add(coefficientA * minX  + coefficientB);
        second.add(coefficientA * maxX  + coefficientB);
        return new Series(first, second);
    }

    static private double getMaxValue() {
        double result = 0;
        for (XYChart.Series<Number, Number>  list : Management.Control.getSeriesList()) {
            for(int i = 0; i < list.getData().size(); i++) {
               if(result < list.getData().get(i).getXValue().doubleValue())
                   result = list.getData().get(i).getXValue().doubleValue();
            }
        }
        return result;
    }

    static private double getMinValue() {
        double result = 0;
        for (XYChart.Series<Number, Number>  list : Management.Control.getSeriesList()) {
            for(int i = 0; i < list.getData().size(); i++) {
                if(result > list.getData().get(i).getXValue().doubleValue())
                    result = list.getData().get(i).getXValue().doubleValue();
            }
        }
        return result;
    }

    static public void removeAllTrendFromLegend () {
        for (Node n : Management.Control.getLineChart().getChildrenUnmodifiable()) {
            if (n instanceof Legend) {
                Legend l = (Legend) n;
                l.getItems().remove(Management.Control.getSeriesList().size(), l.getItems().size());
                break;
            }
        }
    }

    public static List<Double> linspace(double start, double stop, int n)
    {
        List<Double> result = new ArrayList<Double>();

        double step = (stop-start)/(n-1);

        for(int i = 0; i <= n-2; i++)
        {
            result.add(start + (i * step));
        }
        result.add(stop);

        return result;
    }
}
