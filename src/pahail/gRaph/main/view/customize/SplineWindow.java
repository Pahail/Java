package pahail.gRaph.main.view.customize;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pahail.gRaph.main.core.*;
import pahail.gRaph.main.view.Pane.TextFieldForNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SplineWindow {

    private Stage splineStage;

    public SplineWindow(int seriesId) {
        splineStage = new Stage();

        Button splineButton = new Button("Spline");
        splineButton.setTooltip(new Tooltip("Create cubic spline"));
        splineButton.setDisable(false);
        splineButton.setTranslateX(200);
        AtomicReference<Spline> spline = new AtomicReference<>();

        splineButton.setOnAction(e -> {

            ObservableList<XYChart.Data<Number, Number> > target = Management.Control.getSeriesList().get(seriesId).getData();
            List<Double> first = new ArrayList<>();
            List<Double> second = new ArrayList<>();
            for (int i = 0; i < target.size(); i++) {
                first.add((Double) target.get(i).getXValue());
                second.add((Double) target.get(i).getYValue());
            }

            boolean success = true;
            //Проверяем, удалось ли создать сплайн
            try {
                spline.set(new Spline(first, second));
            } catch (Exception e1) {
                success = false;
            }
            //Если удалось, создаем и добавляем серию данных
            if(success) {
                Management.Control.getStyleList().get(seriesId).setCubicSpline(spline.get());
                double a = first.get(0);
                double b = first.get(first.size() - 1);
                first = OLS.linspace(a, b, 200);
                second.clear();
                for (int i = 0; i < first.size(); i++) {
                    try {
                        second.add(spline.get().getValue(first.get(i)));
                    } catch (Exception e2) {
                        System.err.println("Wrong input data");
                    }
                }
                //System.out.println(first.size() + " " + second.size());
                Series series = new Series(first, second);

                XYChart.Series<Number, Number> seriesData = new XYChart.Series<>();
                seriesData.setData(series.getSeries());
                seriesData.setName("spline");
                //Избавляемся от дублирования линий тренда при повторном нажатии
                ChartStyleMaker currentSeriesInfo = Management.Control.getStyleList().get(seriesId);
                if (currentSeriesInfo.isOls()) {
                    Management.Control.getLineChart().getData().remove(currentSeriesInfo.getIdNumberOls());
                    Management.Control.getLineChart().getData().add(currentSeriesInfo.getIdNumberOls(), seriesData);
                } else {
                    Management.Control.getLineChart().getData().add(seriesData);
                    currentSeriesInfo.setOls(true);
                }
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

                //Удаляем из легенды все новые сплайны
                OLS.removeAllTrendFromLegend();
            }

        });


        splineStage.initModality(Modality.APPLICATION_MODAL);
        splineStage.setTitle("Series #" + (seriesId + 1) + " spline" );
        splineStage.setX(Management.Control.getPrimaryStage().getX() + 350);
        splineStage.setY(Management.Control.getPrimaryStage().getY() + 65);
        splineStage.setResizable(false);

        AnchorPane group = new AnchorPane();
        VBox body = new VBox();
        body.setSpacing(5);

        HBox getValue = new HBox();
        getValue.setTranslateX(10);
        getValue.setSpacing(10);

        HBox getDerivativeValue = new HBox();
        getDerivativeValue.setTranslateX(10);
        getDerivativeValue.setSpacing(10);


        Scene splineScene = new Scene(group,280,310);
        splineStage.setScene(splineScene);

//        Скрыть показать линию регрессии
//        HideRegressionTool hideRegressionTool = new HideRegressionTool(seriesId);
//        regressionField.getChildren().add(hideRegressionTool.getTool());
//        hideRegressionTool.setSwitcher(Management.Control.getStyleList().get(seriesId).isOls() );

        splineStage.show();

        TextFieldForNumber input1 = new TextFieldForNumber();
        input1.setText("0.0");
        input1.setPrefWidth(100);
        Button confirm1 = new Button("Get value");
        confirm1.setPrefWidth(140);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(spline.get() == null) {
            spline.set(Management.Control.getStyleList().get(seriesId).getCubicSpline());
        }
        confirm1.setOnAction(e -> {
            String value = null;
            double x = input1.getValue();
            alert.setTitle("Value in " + x);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            if(x > spline.get().getMaxX()) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Extrapolated Value in " + x);
            }

            try {
                value = "" + spline.get().getValue(x);
            } catch (Exception ex) {
                value = "ERROR\n " + x + " doesn't belong interpolation segment";
            };
            alert.setContentText("Spline( "  + x + " ) = \n" +
                    value );
            alert.showAndWait();
        });

        TextFieldForNumber input2 = new TextFieldForNumber();
        input2.setPrefWidth(100);
        input2.setText("0.0");
        Button confirm2 = new Button("Get d/dx value");
        confirm2.setPrefWidth(140);
        if(spline.get() == null) {
            spline.set(Management.Control.getStyleList().get(seriesId).getCubicSpline());
        }
        confirm2.setOnAction(e -> {
            String value = null;
            double x = input2.getValue();
            alert.setTitle("Value in " + x);
            try {
                value = "" + spline.get().getDerivativeValue(x);
            } catch (Exception ex) {
                value = "ERROR\n " + x + " doesn't belong interpolation segment";
            };
            alert.setContentText("Derivative in "  + x + "  = \n" +
                    value );
            alert.showAndWait();
        });

        getValue.getChildren().addAll(input1, confirm1);
        getDerivativeValue.getChildren().addAll(input2, confirm2);

        body.getChildren().addAll(splineButton, getValue, getDerivativeValue);

        //Часть, отвечающая за кнопки управления
        HBox controlButtons = new HBox();
        controlButtons.setTranslateX(100);
        controlButtons.setSpacing(5);

//        Button acceptButton = new Button("Accept");
//        acceptButton.setOnAction(e -> {
//            hideRegressionTool.hideRegression(seriesId, hideRegressionTool.isSwitcher());
//            splineStage.hide();
//        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            splineStage.hide();
        });

        splineScene.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ESCAPE)) {
                splineStage.hide();
            }
        });

        controlButtons.getChildren().addAll(cancelButton);
        group.getChildren().addAll(body, controlButtons);
        AnchorPane.setTopAnchor(body, 10.0);
        AnchorPane.setBottomAnchor(controlButtons, 10.0);
    }

}
