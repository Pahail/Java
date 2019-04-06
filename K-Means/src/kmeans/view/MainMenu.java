package kmeans.view;

import javafx.scene.control.*;
import javafx.stage.Screen;

public class MainMenu extends MenuBar {
    private MenuItem menuOpen;
    private MenuItem menuClear;
    private MenuItem menuSave;
    private MenuItem menuExit;
    private MenuItem menuAbout;
    private MenuItem menuRandom;

    public MenuItem getMenuOpen() {
        return menuOpen;
    }

    public MenuItem getMenuClear() {
        return menuClear;
    }

    public MenuItem getMenuExit() {
        return menuExit;
    }

    public MenuItem getMenuSave() {
        return menuSave;
    }

    public MenuItem getMenuAbout() {
        return menuAbout;
    }

    public MenuItem getMenuRandom() {
        return menuRandom;
    }

    public MainMenu() {
        Menu menu = new Menu("File");
        Menu help = new Menu("Help");
        menuOpen = new MenuItem("Open");
        menuClear = new MenuItem("Clear");
        menuSave = new MenuItem("Save");
        menuExit = new MenuItem("Exit");
        menu.getItems().addAll(menuOpen, menuSave, menuClear, new SeparatorMenuItem(),  menuExit);
        menuAbout = new MenuItem("About");
        menuRandom = new MenuItem("Random\npoints");
        help.getItems().addAll(menuRandom, menuAbout);
        getMenus().addAll(menu, help);
        setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());

    }
}
