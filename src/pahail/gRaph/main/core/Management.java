package pahail.gRaph.main.core;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pahail.gRaph.main.view.SeriesChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum Management {
    Control, View, Screen;

    private int screenWidth = 920;
    private int screenHeight = 540;

    private File file;                                                     //Открытый файл
    private Stage primaryStage;                                            //Главное окно
    private VBox rootGroup;                                                //Pane с ключевыми панелями
    private ObservableList<XYChart.Series<Number, Number> > seriesList;    //Список с сериями данных
    private ObservableList<String> seriesNames;                            //Список для ChoiceBox
    private SeriesChooser seriesChooser;                                   //ChoiceBox
    private List<ChartStyleMaker> styleList;                               //Стили для графиков
    private ColorPicker colorPicker;                                       // Палитра цветов, пусть будет одна на всех
    private MenuItem settingsButton;
    private Label nameLabel;

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public MenuItem getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(MenuItem settingsButton) {
        this.settingsButton = settingsButton;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }

    public void setLineChart(LineChart<Number, Number> lineChart) {
        this.lineChart = lineChart;
    }

    private LineChart<Number, Number> lineChart;

    public List<ChartStyleMaker> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<ChartStyleMaker> styleList) {
        this.styleList = styleList;
    }

    public SeriesChooser getSeriesChooser() {
        return seriesChooser;
    }

    public void setSeriesChooser(SeriesChooser seriesChooser) {
        this.seriesChooser = seriesChooser;
    }

    public ObservableList<String> getSeriesNames() {
        return seriesNames;
    }

    public void setSeriesNames(ObservableList<String> seriesNames) {
        this.seriesNames = seriesNames;
    }

    public ObservableList<XYChart.Series<Number, Number>> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(ObservableList<XYChart.Series<Number, Number>> seriesList) {
        this.seriesList = seriesList;
    }

    public MenuItem getCloseButton() {
        return CloseButton;
    }

    public void setCloseButton(MenuItem closeButton) {
        CloseButton = closeButton;
    }

    private MenuItem CloseButton;




    public VBox getRootGroup() {
        return rootGroup;
    }

    public void setRootGroup(VBox radioButtonsTools) {

        this.rootGroup = radioButtonsTools;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {

        return primaryStage;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {

        this.file = file;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {

        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void initStyleList() {
        styleList = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            styleList.add(new ChartStyleMaker(i));
        }
    }

}
