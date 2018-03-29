package pahail.the15game.core;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pahail.the15game.Background;
import pahail.the15game.Const;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FinalScreen.oldStage = primaryStage;
        startNewGame(primaryStage);
        primaryStage.show();
    }

    public static void startNewGame(Stage primaryStage)  {
        Group root = new Group();
        root.getChildren().add(new Background().getBackground());
        GameLogic gl = new GameLogic();
        root.getChildren().add(gl.newGame());

        Scene scene = new Scene(root, Const.SIZE * 9, Const.SIZE * 9, Color.GRAY);
        primaryStage.setScene(scene);
        primaryStage.setTitle("the15game");
        primaryStage.setResizable(false);
    }

}
