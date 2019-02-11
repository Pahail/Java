package pahail.gRaph.main.view.customize;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pahail.gRaph.main.core.ChartStyleMaker;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.core.OLS;
import pahail.gRaph.main.core.Series;
import pahail.gRaph.main.view.buttons.ToggleSwitch;

public class HideRegressionTool {
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

    public HideRegressionTool(int seriesId) {
        tool = new HBox();
        tool.setSpacing(30);
        Label label = new Label("Add/Remove regression");
        label.setTranslateX(10);
        label.setTranslateY(5);
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        switcher = toggleSwitch;
        tool.getChildren().addAll(label, toggleSwitch);
    }

    public void hideRegression (int seriesId, boolean hide)  {
        //TODO Добавить стили css
        ChartStyleMaker currentSeriesInfo = Management.Control.getStyleList().get(seriesId);
        if(!hide ) {
            if(currentSeriesInfo.isOls()) {
                Management.Control.getLineChart().getData().set(currentSeriesInfo.getIdNumberOls(), new XYChart.Series<>() );
                currentSeriesInfo.setOls(false);
                //Удаляем из легенды все линии тренда
                OLS.removeAllTrendFromLegend();
            }
        } else {
            if(!currentSeriesInfo.isOls()) {
                Series series = OLS.getOLS(seriesId);
                XYChart.Series<Number, Number> seriesData = new XYChart.Series<>();
                seriesData.setData(series.getSeries());
                seriesData.setName("trend");
                Management.Control.getLineChart().getData().add(seriesData);
                currentSeriesInfo.setOls(true);
                int id = Management.Control.getLineChart().getData().indexOf(seriesData);
                currentSeriesInfo.setIdNumberOls(id);
                //Задаем тот же цвет
                String style = "-fx-stroke:" + ChartStyleMaker.getStringColor(currentSeriesInfo.getColor()) + ";";
                Management.Control.getLineChart().getData().get(id).getNode().lookup(".chart-series-line").setStyle(style);
                //Удаляем символы в точках
                XYChart.Series<Number, Number> s = Management.Control.getLineChart().getData().get(id);
                for (XYChart.Data<Number, Number> d : s.getData()) {
                    if (d.getNode() != null) {
                        d.getNode().setVisible(false);
                    }
                }
                //Удаляем из легенды все линии тренда
                OLS.removeAllTrendFromLegend();
            }
        }



    }


}
