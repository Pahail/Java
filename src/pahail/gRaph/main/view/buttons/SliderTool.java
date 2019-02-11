package pahail.gRaph.main.view.buttons;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class SliderTool {
    private HBox tool;
    private Slider slider;

    public HBox getTool() {
        return tool;
    }

    public int getIntValue() {
        return (int) slider.getValue();
    }

    public double getValue() {
        return slider.getValue();
    }

    public SliderTool(String text, String tooltip,double min, double max, double currentValue) {
        tool = new HBox();
        tool.setSpacing(30);
        Label label = new Label(text);
        label.setTranslateX(10);
        label.setTranslateY(5);
        slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(currentValue);
        slider.setTooltip(new Tooltip(tooltip));
        slider.setTranslateY(5);
        tool.getChildren().addAll(label, slider);
    }

    public SliderTool(String text, String tooltip, double currentValue) {
        this(text, tooltip, 1, 100, currentValue);
    }



    }
