package pahail.gRaph.main.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;
import pahail.gRaph.main.core.*;
import pahail.gRaph.main.view.customize.CustomizeWindow;
import pahail.gRaph.main.view.customize.RegressionWindow;
import pahail.gRaph.main.view.customize.SplineWindow;

import java.util.ArrayList;
import java.util.List;

public class SeriesChooser {
    private ChoiceBox<String> seriesChooser;
    private Label selectSeriesLabel;
    private Button customize;
    private Button splineButton;
    private Button regressionWindow;
    private Button splineWindow;

    public Button getSplineWindow() {
        return splineWindow;
    }

    public Button getRegressionWindow() {
        return regressionWindow;
    }

    public Button getSplineButton() {
        return splineButton;
    }

    public Label getSelectSeriesLabel() {
        return selectSeriesLabel;
    }

    public Button getCustomize() {
        return customize;
    }

    public void setSelectSeriesLabel(Label selectSeriesLabel) {
        this.selectSeriesLabel = selectSeriesLabel;
    }

    public ChoiceBox getSeriesChooser() {
        return seriesChooser;
    }

    public void setSeriesChooser(ChoiceBox<String> seriesChooser) {
        this.seriesChooser = seriesChooser;
    }

    public SeriesChooser() {
        seriesChooser = new ChoiceBox<>();
        selectSeriesLabel = new Label("Select series:");
        selectSeriesLabel.setTranslateY(4);
        seriesChooser.setTooltip(new Tooltip("Select series to customize"));
        seriesChooser.setPrefWidth(90);
        seriesChooser.setDisable(true);
        selectSeriesLabel.setStyle("-fx-text-fill: grey;");

        customize = new Button("Customize");
        customize.setTooltip(new Tooltip("Customize selected series"));
        customize.setDisable(true);

        regressionWindow = new Button("Regression window");
        regressionWindow.setTooltip(new Tooltip("Set regression"));

        regressionWindow.setDisable(true);


        regressionWindow.setOnAction(e -> {
            RegressionWindow regWin = new RegressionWindow(getSelectedIndex());

        });

        //Устаревший функционал
        splineButton = new Button("Spline");
        splineButton.setTooltip(new Tooltip("Create cubic spline"));
        splineButton.setDisable(true);
        SVGPath triangle = new SVGPath();
        String triangleDown = "M 10 10 L 20 10 L 15 20 z";
        triangle.setContent(triangleDown);
        splineWindow = new Button("",triangle);
        splineWindow.setDisable(true);
        splineWindow.setTranslateX(-10);
        splineWindow.setOnAction(e ->{
            SplineWindow sp = new SplineWindow(getSelectedIndex());
        });

        splineButton.setOnAction(e -> {
            Series series = null;

            ObservableList<XYChart.Data<Number, Number> > target = Management.Control.getSeriesList().get(getSelectedIndex()).getData();
            List<Double> first = new ArrayList<>();
            List<Double> second = new ArrayList<>();
            for (int i = 0; i < target.size(); i++) {
                first.add((Double) target.get(i).getXValue());
                second.add((Double) target.get(i).getYValue());
            }
            Spline spline = null;
            boolean succes = true;
            //Проверяем, удалось ли создать сплайн
            try {
                spline = new Spline(first, second);
            } catch (Exception e1) {
                succes = false;
            }
            //Если удалось, создаем и добавляем серию данных
            if(succes) {
                Management.Control.getStyleList().get(getSelectedIndex()).setCubicSpline(spline);
                double a = first.get(0);
                double b = first.get(first.size() - 1);
                first = OLS.linspace(a, b, 200);
                second.clear();
                for (int i = 0; i < first.size(); i++) {
                    try {
                        second.add(spline.getValue(first.get(i)));
                    } catch (Exception e2) {
                        System.err.println("Wrong input data");
                    }
                }
                //System.out.println(first.size() + " " + second.size());
                series = new Series(first, second);

                XYChart.Series<Number, Number> seriesData = new XYChart.Series<>();
                seriesData.setData(series.getSeries());
                seriesData.setName("spline");
                //Избавляемся от дублирования линий тренда при повторном нажатии
                ChartStyleMaker currentSeriesInfo = Management.Control.getStyleList().get(getSelectedIndex());
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

        customize.setOnAction(e-> {
            int selectedSeriesId = getSelectedIndex();
            ChartStyleMaker chartStyleMaker = new ChartStyleMaker(selectedSeriesId);
            CustomizeWindow customizeWindow = new CustomizeWindow(selectedSeriesId);
        });
    }

    public void seriesChooserSetOn(boolean active) {
        if(active) {
            selectSeriesLabel.setStyle("-fx-text-fill: black;");
            seriesChooser.setItems(Management.Control.getSeriesNames());
            seriesChooser.setValue(Management.Control.getSeriesNames().get(0));
            customize.setDisable(false);
            splineButton.setDisable(false);
            regressionWindow.setDisable(false);
            splineWindow.setDisable(false);
        } else {
            selectSeriesLabel.setStyle("-fx-text-fill: grey;");
            customize.setDisable(true);
            splineButton.setDisable(true);
            regressionWindow.setDisable(true);
            splineWindow.setDisable(true);
            //Таким образом можно сбросить выделение в choice box
            if(Management.Control.getSeriesNames() != null) {
                Management.Control.getSeriesNames().clear();
            }
        }
        seriesChooser.setDisable(!active);
    }

    public int getSelectedIndex() {
       return Management.Control.getSeriesNames().indexOf(seriesChooser.getValue());
    }

}
