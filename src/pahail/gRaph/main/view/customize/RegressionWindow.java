package pahail.gRaph.main.view.customize;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.view.Pane.HiddenPane;

public class RegressionWindow {

    private Stage regressionStage;

    public RegressionWindow(int seriesId) {
        regressionStage = new Stage();

        regressionStage.initModality(Modality.APPLICATION_MODAL);
        regressionStage.setTitle("Series #" + (seriesId + 1) + " regression" );
        regressionStage.setX(Management.Control.getPrimaryStage().getX() + 310);
        regressionStage.setY(Management.Control.getPrimaryStage().getY() + 65);
        regressionStage.setResizable(false);

        AnchorPane group = new AnchorPane();
        VBox body = new VBox();
        HBox regressionField = new HBox();

        regressionField.setSpacing(10);
        Scene regression = new Scene(group,280,410);
        regressionStage.setScene(regression);

        //Скрыть показать линию регрессии
        HideRegressionTool hideRegressionTool = new HideRegressionTool(seriesId);
        regressionField.getChildren().add(hideRegressionTool.getTool());
        hideRegressionTool.setSwitcher(Management.Control.getStyleList().get(seriesId).isOls() );

        regressionStage.show();

        //Тестирую панельку
        HiddenPane hiddenPane = new HiddenPane();
        hiddenPane.setTitle("test");
        Button hb = new Button("hidden button");
        hiddenPane.getBody().add(hb);


        //Часть, отвечающая за кнопки управления
        HBox controlButtons = new HBox();
        controlButtons.setTranslateX(80);
        controlButtons.setSpacing(5);

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {
            hideRegressionTool.hideRegression(seriesId, hideRegressionTool.isSwitcher());
            regressionStage.hide();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            regressionStage.hide();
        });

        regression.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ESCAPE)) {
               regressionStage.hide();
            }
        });

        controlButtons.getChildren().addAll(acceptButton, cancelButton);
        group.getChildren().addAll(body, hiddenPane, controlButtons);
        //Тестовое поле
        body.getChildren().add(regressionField);
        body.getChildren().add(hiddenPane);
        AnchorPane.setTopAnchor(body, 10.0);
        AnchorPane.setBottomAnchor(controlButtons, 10.0);
    }


}
