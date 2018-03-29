package pahail.the15game.core;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import pahail.the15game.Const;
import pahail.the15game.core.Main;

public class FinalScreen {
    private Stage finalScreen = new Stage();
    static volatile Stage oldStage = null;

    FinalScreen() {
        finalScreen.setTitle("Congratulation");
        finalScreen.setResizable(false);
        Alert errorNewGameAlert = new Alert(Alert.AlertType.ERROR);
        errorNewGameAlert.setHeaderText("Error");
        errorNewGameAlert.setContentText("Game was crushed");
        TilePane root = new TilePane();
        Button newGameButton  = new Button("New Game");
        Button exitButton  = new Button("Exit");
        root.setTranslateX(Const.SIZE);
        root.alignmentProperty();
        root.setOrientation(Orientation.VERTICAL);
        root.getChildren().add(newGameButton);
        root.getChildren().add(exitButton);
        root.setVgap(10);
        newGameButton.setOnMouseClicked(e-> {
            finalScreen.hide();
            try {
                Main.startNewGame(oldStage);

            } catch (Exception ex) {
                errorNewGameAlert.showAndWait();
                System.exit(-1);
            }
            e.consume();
        });
        exitButton.setOnMouseClicked(e-> {
            System.exit(0);
            e.consume();

        });
        Scene scene = new Scene(root,Const.SIZE * 4, Const.SIZE * 2);
        finalScreen.setScene(scene);
        moveFinalWindow();

        //Обработка закрытия окон
        finalScreen.setOnCloseRequest(event -> {
            oldStage.close();
        });
        oldStage.setOnCloseRequest(event -> {
            finalScreen.close();
            System.exit(0); //Что бы остановить поток в методе moveFinalWindow
        });
    }

    public Stage getFinalScreen() {
        return finalScreen;
    }


    //Задает координаты нового окна посреди основного окна
    private void moveFinalWindow() {
        //Анонимная функция - просто эксперементирую
        Runnable r = () -> {
            while(!finalScreen.isShowing()) {
                finalScreen.setX(oldStage.getX() + Const.SIZE * 2.5);
                finalScreen.setY(oldStage.getY() + Const.SIZE * 2);
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }
}
