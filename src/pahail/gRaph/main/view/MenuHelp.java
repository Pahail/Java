package pahail.gRaph.main.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class MenuHelp {
    public Menu menuHelp = null;

    public MenuHelp() {
        menuHelp = new Menu("Help");
        ImageView imgHlp = new ImageView("file:/home/wraith/Desktop/Programing/gRaph/src/pahail/gRaph/main/resources/hlp.png");
        imgHlp.setFitHeight(24);
        imgHlp.setFitWidth(24);
        menuHelp.setGraphic(imgHlp);
        MenuItem menuManual = createMenuManual();
        MenuItem menuAbout = createMenuAbout();
        menuHelp.getItems().addAll(menuManual, menuAbout);
    }

    private MenuItem createMenuManual() {
        MenuItem menuManual = new MenuItem("Manual  ");
        menuManual.setOnAction(e -> {
            WebPageWindow manual = new WebPageWindow(false,"https://www.vk.com/id223666681", "Manual", 340, 410);
            manual.getWebStage().show();
        });
        return menuManual;
    }

    private MenuItem createMenuAbout() {
        MenuItem menuAbout = new MenuItem("About");
        menuAbout.setOnAction(e -> {
            WebPageWindow manual = new WebPageWindow(true,"/home/wraith/Desktop/Programing/gRaph/src/pahail/gRaph/main/resources/about.html", "About", 340, 350);
            manual.getWebStage().show();
        });
        return menuAbout;
    }
}
