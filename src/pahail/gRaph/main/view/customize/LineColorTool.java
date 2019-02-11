package pahail.gRaph.main.view.customize;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import pahail.gRaph.main.core.Management;

public class LineColorTool {

    private HBox tool;
    private ColorPicker colorPicker;

    public HBox getTool() {
        return tool;
    }

    public void setTool(HBox tool) {
        this.tool = tool;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public LineColorTool(int seriesId) {
        tool = new HBox();
        tool.setSpacing(30);
        Label lineColor = new Label("Line color  ");
        lineColor.setTranslateX(10);
        lineColor.setTranslateY(5);
        colorPicker = Management.Control.getColorPicker();
        colorPicker.setValue(Management.Control.getStyleList().get(seriesId).getColor() );
        colorPicker.setPrefWidth(160);
        tool.getChildren().addAll(lineColor, colorPicker);
    }

    public LineColorTool(int seriesId, String string, ColorPicker colorPicker) {
        tool = new HBox();
        tool.setSpacing(30);
        Label lineColor = new Label(string);
        lineColor.setTranslateX(10);
        lineColor.setTranslateY(5);
        colorPicker.setValue(Management.Control.getStyleList().get(seriesId).getColor() );
        colorPicker.setPrefWidth(160);
        tool.getChildren().addAll(lineColor, colorPicker);
    }

    public Color getLineColor() {
        return colorPicker.getValue();
    }


}
