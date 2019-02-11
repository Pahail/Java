package pahail.gRaph.main.view;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import pahail.gRaph.main.core.Management;

import java.io.File;

public class MenuFile {
    public Menu menuFile = null;
    private File newFile = null;


    public  MenuFile() {
        menuFile = new Menu("File");
        ImageView imgFile = new ImageView("file:/home/wraith/Desktop/Programing/gRaph/src/pahail/gRaph/main/resources/file.png");
        imgFile.setFitHeight(24);
        imgFile.setFitWidth(24);
        menuFile.setGraphic(imgFile);
        MenuItem menuOpen = createMenuOpen();
        MenuItem menuExit = createMenuExit();
        MenuItem menuClose = createMenuClose();
        SeparatorMenuItem separator = new SeparatorMenuItem();
        menuFile.getItems().addAll(menuOpen, menuClose, separator, menuExit);

    }

    private MenuItem createMenuOpen() {
        MenuItem menuOpen =  new MenuItem("Open");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        menuOpen.setOnAction(e -> {
            newFile = fileChooser.showOpenDialog( Management.Control.getPrimaryStage());
            openFile(newFile);
        } );
        return menuOpen;
    }

    public static void openFile( File target) {
        if(target != null) {
            if(Management.Control.getRootGroup().getChildren().size() > 3) {
                closeFile();
            }
            Management.Control.setFile(target);
            PlotPane plot = new PlotPane();
            if(plot.lineChart != null) {
                Management.Control.getRootGroup().getChildren().add(plot.plotPane);
                Management.Control.getCloseButton().setDisable(false);
                Management.Control.getSeriesChooser().seriesChooserSetOn(true);
                Management.Control.getSettingsButton().setDisable(false);
            }
        }


    }

    private MenuItem createMenuClose() {
        MenuItem menuClose = new MenuItem("Close");
        Management.Control.setCloseButton( menuClose);
        menuClose.setDisable(true);
        menuClose.setOnAction(e -> {
            closeFile();
            Management.Control.getRootGroup().getChildren().add(new DefaultPlotPane().defaultPlotPane);
        });
        return menuClose;
    }

    private static void closeFile() {
        Management.Control.setFile(null);
        int size = Management.Control.getRootGroup().getChildren().size();
        //Просто удаляем последние элементы из VBox на главном экране
        if(size > 3){
            Management.Control.getRootGroup().getChildren().remove(size - 1);
            Management.Control.getCloseButton().setDisable(true);
            Management.Control.getSeriesChooser().seriesChooserSetOn(false);
            Management.Control.getSettingsButton().setDisable(true);

        }
    }

    private MenuItem createMenuExit() {
        MenuItem menuExit = new MenuItem("Exit");
        menuExit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        menuExit.setOnAction(e -> {System.exit(0);});
        return menuExit;
    }

}
