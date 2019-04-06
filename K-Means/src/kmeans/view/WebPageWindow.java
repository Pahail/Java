package kmeans.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class WebPageWindow {

    private Stage webStage;

    public Stage getWebStage() {
        return webStage;
    }

    public WebPageWindow(Boolean isLocal, String link, String name, int width, int height) {
        webStage = new Stage();
        webStage.initModality(Modality.APPLICATION_MODAL);
        webStage.setTitle(name);
        webStage.setResizable(false);
        AnchorPane webPane = new AnchorPane();
        HBox controlTool = new HBox();
        controlTool.setSpacing(10);
        Scene webPage = new Scene(webPane,width,height);
        webStage.setScene(webPage);

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        AnchorPane.setTopAnchor(webView, 10.0);
        AnchorPane.setLeftAnchor(webView, 10.0);
        AnchorPane.setRightAnchor(webView, 10.0);
        AnchorPane.setBottomAnchor(webView, 40.0);
        AnchorPane.setBottomAnchor(controlTool, 0.0);
        webPane.getChildren().addAll(webView, controlTool);
        //TODO Написать инструкцию в виде html
        if(isLocal) {
            List<String> html = new ArrayList<>();
            try {
                html = FileUtils.readLines(link);
            } catch (Exception e) {
                System.err.println("Can't open file");
            }
            String line = "";
            for (String s: html) {
                line += s;
            }
            engine.loadContent(line);

        } else {
            engine.load(link);
        }
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(60);
        controlTool.getChildren().add(closeButton);
        HBox.setMargin(closeButton, new Insets(5, 170, 5, 170));
        closeButton.setOnAction(e -> {
            webStage.hide();
        });
        webPage.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ESCAPE)) {
                webStage.hide();
            }
        });



    }
}
