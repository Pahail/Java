package pahail.gRaph.main.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pahail.gRaph.main.core.Management;


public class ConfigTool {
    private BorderPane ConfigPane;
    private HBox ConfigTool;

    public void setConfigPane(BorderPane configPane) {
        this.ConfigPane = configPane;
    }

    public HBox getConfigTool() {
        return ConfigTool;
    }

    public  BorderPane getConfigPane() {
        return ConfigPane;
    }

    public void setConfigTool(HBox configTool) {
        this.ConfigTool = configTool;
    }

    public ConfigTool() {

        ConfigPane = new BorderPane();
        ConfigTool = new HBox();
        ConfigPane.setCenter(ConfigTool);
        BorderPane.setMargin(ConfigTool, new Insets(5, 10, 5, 25));
        ConfigTool.setSpacing(10);
        SeriesChooser seriesChooser = new SeriesChooser();
        Management.Control.setSeriesChooser(seriesChooser);


        ConfigTool.getChildren().addAll(seriesChooser.getSelectSeriesLabel(), seriesChooser.getSeriesChooser());

        ConfigTool.getChildren().addAll(seriesChooser.getCustomize(), seriesChooser.getRegressionWindow(), seriesChooser.getSplineButton(), seriesChooser.getSplineWindow() );

    }
}
