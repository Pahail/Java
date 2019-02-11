package pahail.gRaph.main.core;

import com.sun.javafx.charts.Legend;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChartStyleMaker {

    private int idNumber;
    private int width;
    private Color color;
    private String name;
    private int lineType;
    private boolean isVisible;
    private int idNumberOls;
    private int idNumberSpline;
    private double olsCoefficientA;
    private double olsCoefficientB;
    private boolean ols;
    private boolean spline;
    private String symbol;
    private Legend.LegendItem legend;
    private Spline cubicSpline;
    private double opacity = 1;

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public Spline getCubicSpline() {
        return cubicSpline;
    }

    public void setCubicSpline(Spline cubicSpline) {
        this.cubicSpline = cubicSpline;
    }

    public Legend.LegendItem getLegend() {
        return legend;
    }

    public int getIdNumberSpline() {
        return idNumberSpline;
    }

    public void setIdNumberSpline(int idNumberSpline) {
        this.idNumberSpline = idNumberSpline;
    }

    public boolean isSpline() {
        return spline;
    }

    public void setSpline(boolean spline) {
        this.spline = spline;
    }

    public void setLegend(Legend.LegendItem legend) {
        this.legend = legend;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdNumberOls() {
        return idNumberOls;
    }

    public void setIdNumberOls(int idNumberOls) {
        this.idNumberOls = idNumberOls;
    }

    public double getOlsCoefficientA() {
        return olsCoefficientA;
    }

    public void setOlsCoefficientA(double olsCoefficientA) {
        this.olsCoefficientA = olsCoefficientA;
    }

    public double getOlsCoefficientB() {
        return olsCoefficientB;
    }

    public void setOlsCoefficientB(double olsCoefficientB) {
        this.olsCoefficientB = olsCoefficientB;
    }

    public boolean isOls() {
        return ols;
    }

    public void setOls(boolean ols) {
        this.ols = ols;
    }

    public ChartStyleMaker(int id) {
        this.idNumber = id++;
        this.width = 3;
        this.lineType = 1;
        switch (id) {
            case 1 : {
                this.color = new Color(243.0/255,98.0/255,45.0/255, 1);
                break;
            }
            case 2 : {
                this.color = new Color(251.0/255,167.0/255,27.0/255,1);
                break;
            }
            case 3 : {
                this.color = new Color(87.0/255,183.0/255,87.0/255, 1);
                break;
            }
            case 4 : {
                this.color = new Color(65.0/255,169.0/255,201.0/255, 1);
                break;
            }
            case 5 : {
                this.color = new Color(66.0/255,88.0/255,201.0/255, 1);
                break;
            }
            case 6 : {
                this.color = new Color(154.0/255,66.0/255,200.0/255,1);
                break;
            }
            case 7 : {
                this.color = new Color(200.0/255,65.0/255,100.0/255, 1);
                break;
            }
            case 8 : {
                this.color = new Color(136.0/255,136.0/255,136.0/255, 1);
                break;
            }

            default :
                this.color = new Color(1,0,1,0.3);
        }

        this.name = "Series" + (idNumber + 1);
        this.isVisible = true;
        this.ols = false;
        this.symbol = "Circle";
    }

    //TODO доделать метод или удалить
    public String  styleMaker() {
        List<String> style = new ArrayList<>();
        String result = "";
        style.add(".default-color" + idNumber + ".chart-series-line {");
        style.add("\t-fx-stroke-width: " + width + "px;");

        style.add("\t-fx-stroke: " + getStringColor(this.color) + ";");
        style.add("}");
        String styleName = "pahail/gRaph/main/view/css/chart/custom_style_" + idNumber + ".css";
        //String styleName = "custom_style_" + idNumber + ".css";
       /* try {
            FileUtils.writeLines(styleName, style);
        } catch (Exception e) {
            System.err.println("Can't create css file");
        }*/
        for (String s: style) {
            result += s + "\n";
        }
        return result;
    }

    public static String getStringColor(Color color) {
        int red = (int)(color.getRed() * 255);
        int green = (int)(color.getGreen() * 255);
        int blue = (int)(color.getBlue() * 255);
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}
