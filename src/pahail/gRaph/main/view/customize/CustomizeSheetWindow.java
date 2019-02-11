package pahail.gRaph.main.view.customize;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.view.Pane.HiddenPane;
import pahail.gRaph.main.view.buttons.SliderTool;

public class CustomizeSheetWindow {

    private Stage customizeStage;

    public Stage getCustomizeStage() {
        return customizeStage;
    }

    public CustomizeSheetWindow() {
        customizeStage = new Stage();
        customizeStage.initModality(Modality.APPLICATION_MODAL);
        customizeStage.setTitle("Customize sheet");
        customizeStage.setX(Management.Control.getPrimaryStage().getX() + 310);
        customizeStage.setY(Management.Control.getPrimaryStage().getY() + 65);
        customizeStage.setResizable(false);
        AnchorPane group = new AnchorPane();
        VBox configField = new VBox();
        configField.setSpacing(10);
        configField.setMinWidth(290);
        Scene customize = new Scene(group,300,440);

        customizeStage.setScene(customize);


        NameSetTool nameTool = new NameSetTool("Name of plot", Management.Control.getNameLabel().getText(), "Enter name of plot"  );
        configField.getChildren().add(nameTool.getTool());

        HiddenPane axisRange = new HiddenPane(true);
        axisRange.setTitle("Set axis range");
        NumberAxis xAxis = (NumberAxis) Management.Control.getLineChart().getXAxis();
        FromToSetter xAxisRange = new FromToSetter("Set X range", xAxis.getLowerBound(), xAxis.getUpperBound());
        VBox.setMargin(xAxisRange.getTool(), new Insets(5, 0, 5, 0));

        NumberAxis yAxis = (NumberAxis) Management.Control.getLineChart().getYAxis();
        FromToSetter yAxisRange = new FromToSetter("Set Y range", yAxis.getLowerBound(), yAxis.getUpperBound());
        axisRange.getBody().addAll(xAxisRange.getTool(), yAxisRange.getTool());
        VBox.setMargin(yAxisRange.getTool(), new Insets(5, 0, 5, 0));
        configField.getChildren().add(axisRange);

        HiddenPane axisGrid = new HiddenPane(true);
        axisGrid.setTitle("Set axis grid");
        double xTickUnit =  (xAxis.getUpperBound() - xAxis.getLowerBound() ) / xAxis.getTickUnit();
        SliderTool xAxisUnit = new SliderTool("Set X axis grid", "X range grid", xTickUnit );
        double yTickUnit =  (yAxis.getUpperBound() - yAxis.getLowerBound() ) / yAxis.getTickUnit();
        SliderTool yAxisUnit = new SliderTool("Set Y axis grid", "Y range grid", yTickUnit );
        axisGrid.getBody().addAll(xAxisUnit.getTool(), yAxisUnit.getTool());
        VBox.setMargin(xAxisUnit.getTool(), new Insets(5, 0, 5, 0));
        VBox.setMargin(yAxisUnit.getTool(), new Insets(5, 0, 10, 0));
        configField.getChildren().add(axisGrid);

        HiddenPane axisNameTool = new HiddenPane(true);
        axisNameTool.setTitle("Set axis name");
        NameSetTool xAxisNameTool = new NameSetTool("Set X axis name", xAxis.getLabel(), "Name for x axis");
        VBox.setMargin(xAxisNameTool.getTool(), new Insets(5, 0, 5, 0));
        NameSetTool yAxisNameTool = new NameSetTool("Set Y axis name", yAxis.getLabel(), "Name for y axis");
        VBox.setMargin(yAxisNameTool.getTool(), new Insets(5, 0, 5, 0));

        axisNameTool.getBody().addAll(xAxisNameTool.getTool(), yAxisNameTool.getTool());
        configField.getChildren().add(axisNameTool);

        HBox controlButtons = new HBox();
        controlButtons.setTranslateX(80);

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {
            if(nameTool.getNameField().getText() == null || nameTool.getNameField().getText().equals("")) {
                Management.Control.getNameLabel().setVisible(false);
            } else {
                Management.Control.getNameLabel().setVisible(true);
            }
            Management.Control.getNameLabel().setText(nameTool.getNameField().getText());

            xAxis.setAutoRanging(false);
            yAxis.setAutoRanging(false);
            xAxis.setLowerBound(xAxisRange.getFirst());
            xAxis.setUpperBound(xAxisRange.getSecond());
            yAxis.setLowerBound(yAxisRange.getFirst());
            yAxis.setUpperBound(yAxisRange.getSecond());
            xAxis.setTickUnit((xAxis.getUpperBound() - xAxis.getLowerBound() ) / xAxisUnit.getIntValue());
            yAxis.setTickUnit((yAxis.getUpperBound() - yAxis.getLowerBound() ) / yAxisUnit.getIntValue());
            xAxis.setLabel(xAxisNameTool.getNameField().getText());
            yAxis.setLabel(yAxisNameTool.getNameField().getText());

            customizeStage.hide();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            customizeStage.hide();
        });

        customize.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ESCAPE)) {
                customizeStage.hide();
            }
        });

        controlButtons.setSpacing(5);
        controlButtons.getChildren().addAll(acceptButton, cancelButton);

        group.getChildren().addAll(configField, controlButtons);
        AnchorPane.setTopAnchor(configField, 10.0);
        AnchorPane.setBottomAnchor(controlButtons, 10.0);
    }

}
