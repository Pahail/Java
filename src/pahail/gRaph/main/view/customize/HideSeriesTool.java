package pahail.gRaph.main.view.customize;

import com.sun.javafx.charts.Legend;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pahail.gRaph.main.core.ChartStyleMaker;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.view.buttons.ToggleSwitch;

public class HideSeriesTool {
    private HBox tool;
    private ToggleSwitch switcher;

    public SimpleBooleanProperty getSwitcher() {
        return switcher.switchOnProperty();
    }


    public boolean isSwitcher() {
            return switcher.switchOnProperty().get();
    }

    public void setSwitcher(boolean switcher) {
        this.switcher.switchOnProperty().setValue(switcher);
    }


    public HBox getTool() {
        return tool;
    }


    public HideSeriesTool(int seriesId) {
        tool = new HBox();
        tool.setSpacing(30);
        Label label = new Label("Show/Hide series  ");
        label.setTranslateX(10);
        label.setTranslateY(5);
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        switcher = toggleSwitch;
        toggleSwitch.setTranslateX(30);
        tool.getChildren().addAll(label, toggleSwitch);
    }

    //Не самый приятный способ скрыть серию на графике
    public static void hideSeries (int seriesId, boolean hide) {
        for (Node n : Management.Control.getLineChart().getChildrenUnmodifiable()) {
            if (n instanceof Legend) {
                Legend l = (Legend) n;
                for (Legend.LegendItem li : l.getItems()) {
                    XYChart.Series<Number, Number> s = Management.Control.getLineChart().getData().get(seriesId);
                    if(Management.Control.getStyleList().get(seriesId).isOls()) {
                        int olsId = Management.Control.getStyleList().get(seriesId).getIdNumberOls();
                        XYChart.Series<Number, Number> ols = Management.Control.getLineChart().getData().get(olsId);
                        ols.getNode().setVisible(!hide);
                    }
                    if (s.getName().equals(li.getText())) {
                        if (hide) {
                            li.getSymbol().setOpacity(0.4);
                        } else {
                            li.getSymbol().setOpacity(1.0);

                        }
                        s.getNode().setVisible(!hide);
                        for (XYChart.Data<Number, Number> d : s.getData()) {
                            if (d.getNode() != null) {
                                d.getNode().setVisible(!hide);
                            }
                        }


                        //Обновляет цвет символа на легенде
                        li.getSymbol().setStyle("-fx-background-color:" + ChartStyleMaker.getStringColor(Management.Control.getColorPicker().getValue()) + ", white;");
                        break;
                    }
                }
            }
        }
    }



}
