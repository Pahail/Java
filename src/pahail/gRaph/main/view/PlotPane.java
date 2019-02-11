package pahail.gRaph.main.view;

/*
*   Класс для отладки и экспериментов
*   по работе с графиками
*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.core.FileUtils;
import pahail.gRaph.main.core.Series;
import pahail.gRaph.main.parse.Parser;


import java.util.List;


public class PlotPane {
    public LineChart<Number, Number> lineChart;
    public BorderPane plotPane;

    public PlotPane() {
        plotPane = new BorderPane();
        NumberAxis y = new NumberAxis();
        NumberAxis x = new NumberAxis();
        lineChart = new LineChart<>(x, y);
        Management.Control.setLineChart(lineChart);
        Label nameLabel = new Label();
        nameLabel.setStyle("-fx-font-size:20px;");
        nameLabel.setMinHeight(50);
        nameLabel.setVisible(false);
        Management.Control.setNameLabel(nameLabel);
        plotPane.setBottom(nameLabel);
        plotPane.setAlignment(nameLabel, Pos.CENTER);


        plotPane.setCenter(lineChart);
        plotPane.setPrefHeight(800);
        lineChart.setStyle("chart-plot-background: red");
        BorderPane.setMargin(lineChart, new Insets(0,10,3,10));

        if(!Management.Control.getFile().isFile()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access error");
            alert.setHeaderText(null);
            alert.setContentText("It is not a file\n" +
                    "Please choose another file");

            alert.showAndWait();
            System.err.println("Can't read file");
            lineChart = null;
            Management.Control.getRootGroup().getChildren().add(new DefaultPlotPane().defaultPlotPane);
            return;

        }

        List<String> lines;
        try {
            lines = FileUtils.readLines(Management.Control.getFile());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access error");
            alert.setHeaderText(null);
            alert.setContentText("No read permission\n" +
                    "Please give read permission or choose another file");

            alert.showAndWait();
            System.err.println("Can't read file");
            lineChart = null;
            Management.Control.getRootGroup().getChildren().add(new DefaultPlotPane().defaultPlotPane);
            return;
        }

        try {
            if (lines != null) {
                List<Series> seriesList = Parser.parseDataFile(lines);
                ObservableList<XYChart.Series<Number, Number> > ChartList = FXCollections.observableArrayList();
                ObservableList<String> listSeriesNames = FXCollections.observableArrayList();
                if(seriesList != null) {
                    int count = 0;
                    for (Series series : seriesList) {
                        count++;
                        XYChart.Series<Number, Number> seriesData = new XYChart.Series<>();
                        if (series.getSeries() == null) {
                            //Если хоть одна из серий не удалась, остальные тоже не строить
                            lineChart = null;
                            Management.Control.getRootGroup().getChildren().add(new DefaultPlotPane().defaultPlotPane);

                            break;
                        } else {
                            seriesData.setData(series.getSeries());
                            seriesData.setName("Series" + count);
                            listSeriesNames.add(seriesData.getName());
                            ChartList.add(seriesData);

                        }
                        lineChart.getData().add(seriesData);
                    }
                    //Инициализация дефолтных стилей графиков
                    Management.Control.initStyleList();

                    Management.Control.setSeriesList(ChartList);
                    Management.Control.setSeriesNames(listSeriesNames);
                } else {
                    lineChart = null;
                    Management.Control.getRootGroup().getChildren().add(new DefaultPlotPane().defaultPlotPane);
                }
            }
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't parse this file");
            alert.setHeaderText(null);
            alert.setContentText("Can't parse this file to double values\n" +
                    "Please check or choose another file");

            alert.showAndWait();
            System.err.println("Can't create plot");
            lineChart = null;
            Management.Control.getRootGroup().getChildren().add(new DefaultPlotPane().defaultPlotPane);
        }

    }

}
