package pahail.gRaph.main.view.customize;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import pahail.gRaph.main.view.Pane.TextFieldForNumber;

public class FromToSetter {
    private HBox tool;
    private TextFieldForNumber first;
    private TextFieldForNumber second;

    public HBox getTool() {
        return tool;
    }

    public double getFirst() {
        return (double) Double.parseDouble(first.getText());
    }

    public double getSecond() {
        return (double) Double.parseDouble(second.getText());
    }

    public FromToSetter(String text, double from, double to) {
        tool = new HBox();
        tool.setSpacing(10);
        Label label = new Label(text);
        label.setTranslateX(10);
        label.setTranslateY(5);
        Label labelFrom = new Label("from");
        labelFrom.setTranslateY(5);
        labelFrom.setTranslateX(5);
        Label labelTo = new Label("to");
        labelTo.setTranslateY(5);
        first = new TextFieldForNumber(from);
        first.setPrefWidth(50);
        second = new TextFieldForNumber(to);
        second.setPrefWidth(50);
        first.setTooltip(new Tooltip("From"));
        second.setTooltip(new Tooltip("To"));
        tool.getChildren().addAll(label, labelFrom, first, labelTo, second);
    }
}
