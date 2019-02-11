package pahail.gRaph.main.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import pahail.gRaph.main.core.Management;

import java.io.File;


public class DefaultPlotPane {
    public BorderPane defaultPlotPane;

    public DefaultPlotPane() {
        NumberAxis y = new NumberAxis();
        NumberAxis x = new NumberAxis();
        LineChart<Number, Number> defaultChart = new LineChart<>(x,y);
        defaultPlotPane = new BorderPane();
        defaultPlotPane.setCenter(defaultChart);
        defaultPlotPane.setPrefHeight(800);

        BorderPane.setMargin(defaultChart, new Insets(0,10,3,10));

        Button dnd = new Button("Drag file to open");
        dnd.setDisable(true);
        dnd.setStyle("-fx-font-size: 18pt; -fx-base: #ECECEC" );
        dnd.setTranslateY(-Management.Screen.getScreenHeight() / 2);
        defaultPlotPane.setBottom(dnd);
        defaultPlotPane.setAlignment(dnd, Pos.CENTER);

        defaultChart.setOnDragOver(event ->  {
            Dragboard db = event.getDragboard();

            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
                defaultChart.setStyle("-fx-background-color:  #ECECEC");
                dnd.setText("Drop file to open");
                dnd.setStyle("-fx-font-size: 18pt; -fx-base: gray" );
            } else {
                event.consume();
            }

        });

        defaultChart.setOnDragExited(event -> {
            defaultChart.setStyle("-fx-background-color:  -fx-background");
            dnd.setText("Drag file to open");
            dnd.setStyle("-fx-font-size: 18pt; -fx-base: #ECECEC" );
        });

        defaultChart.setOnDragDropped(event ->  {
            Dragboard db = event.getDragboard();
            boolean success = false;
            defaultChart.setStyle("-fx-background-color:  -fx-background");
            dnd.setText("Drag file to open");
            dnd.setStyle("-fx-font-size: 18pt; -fx-base: #ECECEC" );
            if (db.hasFiles()) {
                success = true;
                //TODO выглядит довольно неприятно - упрости
                for (File file:db.getFiles()) {
                    if(Management.Control.getRootGroup().getChildren().size() > 3) {
                        Management.Control.setFile(null);
                        int size = Management.Control.getRootGroup().getChildren().size();
                        if(size > 3){
                            Management.Control.getRootGroup().getChildren().remove(size - 1);
                            Management.Control.getCloseButton().setDisable(true);
                            Management.Control.getSeriesChooser().seriesChooserSetOn(false);
                            Management.Control.getSettingsButton().setDisable(true);
                        }
                    }
                    Management.Control.setFile(file);
                    PlotPane plot = new PlotPane();
                    if(plot.lineChart != null) {
                        Management.Control.getRootGroup().getChildren().add(plot.plotPane);
                        Management.Control.getCloseButton().setDisable(false);
                        Management.Control.getSeriesChooser().seriesChooserSetOn(true);
                        Management.Control.getSettingsButton().setDisable(false);

                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });


    }
}
