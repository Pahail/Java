package pahail.gRaph.main.view.customize;

import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pahail.gRaph.main.core.ChartStyleMaker;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.view.buttons.SliderTool;

public class CustomizeWindow {
    private Stage customizeStage;

    public CustomizeWindow(int seriesId) {
        customizeStage = new Stage();
        customizeStage.initModality(Modality.APPLICATION_MODAL);
        customizeStage.setTitle("Customize series #" + (seriesId + 1) );
        customizeStage.setX(Management.Control.getPrimaryStage().getX() + 310);
        customizeStage.setY(Management.Control.getPrimaryStage().getY() + 65);
        customizeStage.setResizable(false);
        AnchorPane group = new AnchorPane();
        VBox configField = new VBox();
        configField.setSpacing(10);
        Scene customize = new Scene(group,280,410);

        customizeStage.setScene(customize);

        //Часть, отвечающая за изменение подписи
        NameSetTool nameSetTool = new NameSetTool("Name of series  ", Management.Control.getStyleList().get(seriesId).getName(), "Enter name of series");
        configField.getChildren().add(nameSetTool.getTool());

        //Часть, отвечающая за отображение серии
        HideSeriesTool hideSeriesTool = new HideSeriesTool(seriesId);
        configField.getChildren().add(hideSeriesTool.getTool());
        hideSeriesTool.setSwitcher(Management.Control.getStyleList().get(seriesId).isVisible());


        //Часть, отвечающая за выбор цвета линии
        LineColorTool lineColorTool = new LineColorTool(seriesId);
        configField.getChildren().add(lineColorTool.getTool());

        //Часть, отвечающая за выбор толщины линии
        HBox lineWidthTool = new HBox();
        lineWidthTool.setSpacing(30);
        Label lineWidth = new Label("Line width  ");
        lineWidth.setTranslateX(10);
        lineWidth.setTranslateY(5);
        Spinner<Integer> setWidth = new Spinner<>(0,20,1);
        setWidth.setTranslateX(-5);
        setWidth.increment(Management.Control.getStyleList().get(seriesId).getWidth() - setWidth.getValue());
        setWidth.setTooltip(new Tooltip("Enter size in pixels"));
        setWidth.setPrefWidth(160);
        lineWidthTool.getChildren().addAll(lineWidth, setWidth);
        configField.getChildren().add(lineWidthTool);

        //Часть, отвечающая за выбор прозрачности линии
        SliderTool lineOpacityTool = new SliderTool("Line opacity", "Set line opacity", 0, 1, Management.Control.getStyleList().get(seriesId).getOpacity());
        lineOpacityTool.getTool().setTranslateY(-5);
        configField.getChildren().add(lineOpacityTool.getTool());


        //Часть, отвечающая за выбор типа линии
        HBox typeOfLineTool = new HBox();                                    
        typeOfLineTool.setSpacing(20);
        Label lineType = new Label("Type of line  ");
        lineType.setTranslateX(10);
        lineType.setTranslateY(5);
        ChoiceBox<Integer> typeOfLine = new ChoiceBox<>();
        //TODO изменить строки на рисунки линий
        ObservableList<Integer> lines =  FXCollections.observableArrayList (1, 2, 4, 8 );
        typeOfLine.setItems(lines);
        typeOfLine.setValue(Management.Control.getStyleList().get(seriesId).getLineType());
        typeOfLine.setPrefWidth(160);
        typeOfLine.setTranslateX(-3);
        typeOfLineTool.getChildren().addAll(lineType, typeOfLine);
        configField.getChildren().add(typeOfLineTool);

        //Часть, отвечающая за выбор типа символа
        HBox typeOfSymbolTool = new HBox();
        typeOfSymbolTool.setSpacing(20);
        Label symbolType = new Label("Symbol view");
        symbolType.setTranslateX(10);
        symbolType.setTranslateY(5);
        ChoiceBox<String> chooseSymbol = new ChoiceBox<>();
        //TODO изменить строки на рисунки символов
        ObservableList<String> symbols =  FXCollections.observableArrayList ("Circle", "Diamond", "Triangle", "Plus", "None" );
        chooseSymbol.setItems(symbols);
        chooseSymbol.setValue(Management.Control.getStyleList().get(seriesId).getSymbol());
        chooseSymbol.setPrefWidth(160);
        chooseSymbol.setTranslateX(-1);
        typeOfSymbolTool.getChildren().addAll(symbolType, chooseSymbol);
        configField.getChildren().add(typeOfSymbolTool);

        //Часть, отвечающая за кнопки управления
        HBox controlButtons = new HBox();
        controlButtons.setTranslateX(40);

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {
            ChartStyleMaker customStyle = Management.Control.getStyleList().get(seriesId);
            customStyle.setColor(lineColorTool.getLineColor());
            customStyle.setWidth(setWidth.getValue() );
            Management.Control.getStyleList().get(seriesId).setOpacity(lineOpacityTool.getValue());
            Management.Control.getLineChart().getData().get(seriesId).getNode().setOpacity(lineOpacityTool.getValue());
            customStyle.setName(nameSetTool.getNameField().getText());
            customStyle.setLineType(typeOfLine.getValue());
            customizeStage.hide();

            Management.Control.getStyleList().get(seriesId).setVisible(hideSeriesTool.isSwitcher());
            HideSeriesTool.hideSeries(seriesId, !hideSeriesTool.isSwitcher());

            String style1 = "-fx-stroke-width: " + setWidth.getValue() + "px;" + "-fx-stroke:"
                    + ChartStyleMaker.getStringColor(lineColorTool.getLineColor()) + ";-fx-stroke-dash-array: 1 "
                    + typeOfLine.getValue() *  setWidth.getValue()  + ";"  ;
            Management.Control.getSeriesList().get(seriesId).getNode().lookup(".chart-series-line").setStyle(style1);
            Management.Control.getSeriesList().get(seriesId).setName(nameSetTool.getNameField().getText());

            //Изменение цвета символа
            XYChart.Series<Number, Number> s = Management.Control.getLineChart().getData().get(seriesId);
            for (XYChart.Data<Number, Number> d : s.getData()) {
                if (d.getNode() != null) {
                    d.getNode().setStyle("-fx-background-color:" + ChartStyleMaker.getStringColor(lineColorTool.getLineColor()) + ", white;");
                }
            }

            String symbolStyle = "";
            switch (chooseSymbol.getValue()) {

                case "Circle" : {
                    symbolStyle = "-fx-background-color: " + ChartStyleMaker.getStringColor(lineColorTool.getLineColor()) + ", white;\n" +
                            "-fx-background-insets: 0, 2;\n" +
                            "-fx-background-radius: 5px;\n" +
                            "-fx-padding: 5px;";
                    Management.Control.getStyleList().get(seriesId).setSymbol("Circle");
                    break;
                }
                case "Diamond" : {
                    symbolStyle = "-fx-background-color: " + ChartStyleMaker.getStringColor(lineColorTool.getLineColor()) + ", white;\n" +
                            "-fx-background-radius: 0;\n" +
                            "-fx-padding: 7px 5px 7px 5px;\n" +
                            "-fx-background-insets: 0, 2.5;\n" +
                            "-fx-shape: \"M5,0 L10,9 L5,18 L0,9 Z\";";
                    Management.Control.getStyleList().get(seriesId).setSymbol("Diamond");
                    break;
                }
                case "Triangle" : {
                    symbolStyle = "-fx-background-color: " + ChartStyleMaker.getStringColor(lineColorTool.getLineColor()) + ";\n" +
                            "-fx-background-radius: 0;\n" +
                             "-fx-background-insets: 0;\n" +
                            "-fx-shape: \"M5,0 L10,8 L0,8 Z\";";
                    Management.Control.getStyleList().get(seriesId).setSymbol("Triangle");
                    break;
                }
                case "Plus" : {
                    symbolStyle = "-fx-background-color: " + ChartStyleMaker.getStringColor(lineColorTool.getLineColor()) + ";\n" +
                            "-fx-background-radius: 0;\n" +
                            "-fx-background-insets: 0;\n" +
                            "-fx-shape: \"M0,10 L0,12 L10,12  L10,22 L12,22 L12,12 L22,12 L22,10 L12,10 L12,0 L10,0 L10,10 Z\";";
                    Management.Control.getStyleList().get(seriesId).setSymbol("Plus");
                    break;
                }
                case "None" : {
                    //TODO Убрать отображение точек
                    symbolStyle = "-fx-shape: \"M0,0  Z\";";
                    Management.Control.getStyleList().get(seriesId).setSymbol("None");
                    break;
                }


            }

            //Поиск нужного объекта в легенде
            for (Node n : Management.Control.getLineChart().getChildrenUnmodifiable()) {
                if (n instanceof Legend) {
                    Legend l = (Legend) n;
                    for (Legend.LegendItem li : l.getItems()) {
                        if (Management.Control.getStyleList().get(seriesId).getName().equals(li.getText())) {
                            Management.Control.getStyleList().get(seriesId).setLegend(li);
                        }

                    }
                }
            }

            for (XYChart.Data<Number, Number> d : s.getData()) {
                if (d.getNode() != null) {
                    d.getNode().setStyle(symbolStyle);
                    //d.getNode().setScaleY(1.5);
                    //d.getNode().setScaleX(1.5);
                    Management.Control.getStyleList().get(seriesId).getLegend().getSymbol().setStyle(symbolStyle);
                }
            }


        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            customizeStage.hide();
        });



        customize.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ESCAPE)) {
                //Чтоб не схлопывалось все, пока открыто окно палитры. Приводит к багу
                if(!lineColorTool.getColorPicker().isFocused()) {
                    customizeStage.hide();
                }
            }
        });

        Button defaultButton = new Button("Default");
        //TODO написать настройки по умолчанию
        //defaultButton.setDisable(true);
        controlButtons.setSpacing(5);
        controlButtons.getChildren().addAll(acceptButton, cancelButton, defaultButton);
        defaultButton.setOnAction(e -> {

        });

        group.getChildren().addAll(configField, controlButtons);
        AnchorPane.setTopAnchor(configField, 10.0);
        AnchorPane.setBottomAnchor(controlButtons, 10.0);

        customizeStage.show();
    }




}
