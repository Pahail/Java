package pahail.the15game.core;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import pahail.the15game.Const;
import pahail.the15game.Tile;

public class FinalScreen {
    private Stage finalScreen = new Stage();
    static Stage mainStage = null;

    FinalScreen() {
        finalScreen.setTitle("Congratulation");
        finalScreen.setResizable(false);

        TilePane root = buttonControl();
        Scene scene = new Scene(root,Const.SIZE * 4, Const.SIZE * 2);
        finalScreen.setScene(scene);
        moveFinalWindow();

        //Обработка закрытия окон
        finalScreen.setOnCloseRequest(event -> {
            mainStage.close();
        });
        mainStage.setOnCloseRequest(event -> {
            finalScreen.close();
            System.exit(0); //Что бы остановить поток в методе moveFinalWindow
        });
    }

    public Stage getFinalScreen() {
        return finalScreen;
    }

    //Создание панели с кнопками и обработка их нажатия
    private TilePane buttonControl() {
        TilePane root = new TilePane();

        Alert errorNewGameAlert = new Alert(Alert.AlertType.ERROR);
        errorNewGameAlert.setHeaderText("Error");
        errorNewGameAlert.setContentText("Game was crushed");

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
            //На случай, если вселенсике силы занулят mainStage
            try {
                Main.startNewGame(mainStage);

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
        return root;
    }


    //Задает координаты нового окна посреди основного окна
    private void moveFinalWindow() {
        //Анонимная функция - просто эксперементирую
        Runnable r = () -> {
            while(!finalScreen.isShowing()) {
                finalScreen.setX(mainStage.getX() + Const.SIZE * 2.5);
                finalScreen.setY(mainStage.getY() + Const.SIZE * 2);
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }
}
