package kmeans.view;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kmeans.controller.Controller;
;

public class MainWindow extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();

    }

    public static void init(Stage primaryStage) {
        Group root = new Group();
        VBox vBox = new VBox();
        AnchorPane  anchorPane = new AnchorPane();
        CanvasForPoints canvas = new CanvasForPoints();
        anchorPane.getChildren().add(canvas);
        AnchorPane.setLeftAnchor(canvas, 20.0);
        AnchorPane.setBottomAnchor(canvas, 5.0);
        AnchorPane.setRightAnchor(canvas, 20.0);
        AnchorPane.setTopAnchor(canvas, 10.0);


        MainMenu mainMenu = new MainMenu();

        Controller controller = new Controller();
        controller.setCanvas(canvas);
        controller.setMainMenu(mainMenu);
        Tool tool = new Tool();

        controller.setTool(tool);
        controller.execute();
        VBox.setMargin(tool, new Insets(0,160,0,160));
        vBox.getChildren().addAll(mainMenu, anchorPane, tool);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 640, 680, Color.WHITESMOKE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("POINTS");
        primaryStage.setResizable(false);
    }
}
