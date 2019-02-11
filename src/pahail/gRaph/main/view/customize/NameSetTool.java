package pahail.gRaph.main.view.customize;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import pahail.gRaph.main.core.Management;

public class NameSetTool {
    private HBox tool;
    private TextField nameField;

    public NameSetTool(String label, String defaultText, String tooltip) {

        tool = new HBox();
        tool.setSpacing(30);
        Label labelName = new Label(label);
        labelName.setTranslateX(10);
        labelName.setTranslateY(5);
        nameField = new TextField();
        nameField.setTranslateX(-5);
        nameField.setPrefWidth(130);
        nameField.setText(defaultText);
        nameField.setTooltip(new Tooltip(tooltip));
        tool.getChildren().addAll(labelName, nameField);
    }

    public HBox getTool() {
        return tool;
    }

    public TextField getNameField() {
        return nameField;
    }
}
