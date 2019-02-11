package pahail.gRaph.main.view;


import javafx.scene.control.MenuBar;

public class MainMenu {
    private MenuBar menuBar;

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public  MainMenu() {
        menuBar = new javafx.scene.control.MenuBar();
        menuBar.setPrefWidth(720);
        ((javafx.scene.control.MenuBar) menuBar).getMenus().add(new MenuFile().menuFile);
        ((javafx.scene.control.MenuBar) menuBar).getMenus().add(new MenuOptions().menuOptions);
        ((javafx.scene.control.MenuBar) menuBar).getMenus().add(new MenuHelp().menuHelp);


    }



}
