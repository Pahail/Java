package pahail.gRaph.main.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.util.List;

public class Series {
    private ObservableList<XYChart.Data<Number, Number>> series = null;

    public ObservableList<XYChart.Data<Number, Number>> getSeries() {
        return series;
    }

    public Series() {
        series = FXCollections.observableArrayList();
        series.add(null);
    }

    public Series(List<Double> first, List<Double> second) {

        if(first.size() != second.size() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't create series");
            alert.setHeaderText(null);
            alert.setContentText("Expected equal numbers of X and Y in every series\n" +
                    "Please check or choose another file");
            System.err.println("Can't create data series");
            alert.showAndWait();
            series = null;
            return;

        }
        series = FXCollections.observableArrayList();
        for(int i = 0; i < first.size(); i++) {
            series.add(new XYChart.Data<>(first.get(i), second.get(i)));
        }
    }
}
