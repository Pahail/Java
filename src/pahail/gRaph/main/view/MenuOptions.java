package pahail.gRaph.main.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import pahail.gRaph.main.core.Management;
import pahail.gRaph.main.view.customize.CustomizeSheetWindow;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MenuOptions {
    public Menu menuOptions = null;
    public MenuOptions() {
        menuOptions = new Menu("Options");
        ImageView imgOpt = new ImageView("file:/home/wraith/Desktop/Programing/gRaph/src/pahail/gRaph/main/resources/opt.png");
        imgOpt.setFitHeight(24);
        imgOpt.setFitWidth(24);
        menuOptions.setGraphic(imgOpt);
        MenuItem test = new MenuItem("test");
        MenuItem shot = new MenuItem("ScreenShot");
        MenuItem sets = new MenuItem("Settings");
        sets.setDisable(true);
        Management.Control.setSettingsButton(sets);
        menuOptions.getItems().addAll(sets, shot, new SeparatorMenuItem(), test);

        test.setOnAction(e ->{
            File testFile = new File("/home/wraith/Desktop/Programing/gRaph/series");
            MenuFile.openFile(testFile);
        });

        sets.setOnAction(e -> {
            CustomizeSheetWindow customizeSheetWindow = new CustomizeSheetWindow();
            customizeSheetWindow.getCustomizeStage().show();
        });


        //TODO Метод косячный и грубо написан, но потом все равно перепишу
        shot.setOnAction(e -> {
            WritableImage image = Management.Control.getRootGroup().getChildren().get(3).snapshot(new SnapshotParameters(), null);
            File file = new File("chart.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException ex) {
                // TODO: handle exception here
            }
        });
    }
}
