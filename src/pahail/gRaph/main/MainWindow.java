package pahail.gRaph.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.view.ConfigTool;
import pahail.gRaph.main.view.DefaultPlotPane;
import pahail.gRaph.main.view.MainMenu;

public class MainWindow extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        initScreen(primaryStage);
        primaryStage.show();
    }

    private void initScreen(Stage primaryStage) {

        //TODO параметры окна, вынесу в класс настроек
        int globalWidth = Management.Screen.getScreenWidth();
        int globalHeight =  Management.Screen.getScreenHeight();

        AnchorPane mainPane = new AnchorPane();

        VBox root = new VBox();
        MainMenu mainMenu = new MainMenu();
        root.getChildren().add( mainMenu.getMenuBar());
        Separator separator = new Separator();
        root.getChildren().add(new ConfigTool().getConfigPane());
        root.getChildren().add(separator);
        root.getChildren().add(new DefaultPlotPane().defaultPlotPane);

        BorderPane plotBorderPane = new DefaultPlotPane().defaultPlotPane;

        mainPane.getChildren().add(root);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);




        Scene scene = new Scene(mainPane, globalWidth, globalHeight);
        Management.Control.setPrimaryStage(primaryStage);
        Management.Control.setRootGroup(root);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(globalHeight / 2);
        primaryStage.setMinWidth(globalWidth / 2);
        primaryStage.setTitle("");
        primaryStage.setResizable(true);
        //Инициализирую палитру
        Management.Control.setColorPicker(new ColorPicker() );
    }

    public static void main(String[] args) {
        launch(args);
    }



}
