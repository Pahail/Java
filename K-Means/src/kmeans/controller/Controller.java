package kmeans.controller;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import kmeans.view.CanvasForPoints;
import kmeans.model.KMeans;
import kmeans.view.MainMenu;
import kmeans.view.Tool;
import kmeans.view.WebPageWindow;
import primitives.Point;
import primitives.PointSet;
import utils.FileUtils;
import utils.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    private CanvasForPoints canvas;
    private MainMenu mainMenu;
    private Tool tool;
    private KMeans kMeans = null;

    public void setCanvas(CanvasForPoints canvas) {
        this.canvas = canvas;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Controller(){

    }

    public void execute(){
        if(canvas == null || mainMenu == null) {
            System.err.println("Should set parameters before executing");
            //TODO придумать исключение получше
            throw new RuntimeException();
        }

        PointSet set = new PointSet(2, 0);
        canvas.initDraw();
        canvas.drawPoints(set.getPoints()); //Отрисовка координатных орт

        mainMenu.getMenuExit().setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        mainMenu.getMenuExit().setOnAction(e -> {System.exit(0);});

        //Очистка полотна и списка точек
        mainMenu.getMenuClear().setOnAction(e -> {
            clear(set.getPoints());
        });

        //Запись множества точек в файл
        mainMenu.getMenuSave().setOnAction(e -> {
            List<String> list = new ArrayList<>();
            for(Point s: set.getPoints()) {
                list.add(s.getX() + " " + s.getY());
            }
            try {
                FileUtils.writeLines("output.txt", list);
            } catch (Exception ex) {
                System.err.println("Can't write in file");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Write error");
                alert.setContentText("Can't write in file");
                alert.show();
            }
        });

        //Открытие точек из файла
        mainMenu.getMenuOpen().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
            List<String> list = null;
            boolean someErrors = false;
            if(file != null) {
                try {
                    list = FileUtils.readLines(file);
                } catch (Exception ex) {
                    System.err.println("Can't read from file: " + file.getPath());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("");
                    alert.setTitle("Open error");
                    alert.setContentText("Can't read from this file:\n" + file.getPath());
                    alert.show();
                }
                clear(set.getPoints());
                List<Double> line = null;
                if(list != null) {
                    int counter = 0;
                    for (String s : list) {
                        try {
                            line = Parser.parseLine(s);
                        } catch (Exception ex1) {
                            System.err.println("Can't parse line #: " + counter);
                            someErrors = true;
                        }
                        counter++;
                        if(line != null && line.size() > 1) {
                            set.getPoints().add(new Point(line.get(0), line.get(1)));
                        }
                    }
                }
            }
            if(someErrors) {
                set.getPoints().clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Parse error");
                alert.setContentText("Can't parse file or some lines from this file:\n" + file.getPath());
                alert.show();
            } else {
                canvas.drawPoints(set.getPoints());
            }
        });


        //Добавление точки в список и ее отрисовка
        canvas.setOnMousePressed(e -> {
            canvas.getGraphicsContext2D().setFill(Color.BLUEVIOLET);
            canvas.addPoint( set.getPoints(), e);
            canvas.drawPoints(set.getPoints());
        });

        //Начало действия K-Means
        //Если алгоритм еще не запускался, то запустить алгоритм и случайно задать центры
        //Если уже запущен, то выполнить итерацию алгоритма
        tool.getStartButton().setOnAction(e -> {
            if(kMeans == null) {
                if(tool.getNumber() > 0 && tool.getNumber() <= set.getPoints().size() ) {
                    kMeans = new KMeans(set.getPoints(), tool.getNumber());
                    canvas.getGraphicsContext2D().setFill(Color.RED);
                    canvas.drawCenters(kMeans.getCenters());
                    tool.getStartButton().setTooltip(new Tooltip("Go to the next iteration"));
                    tool.getStepButton().setDisable(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Illegal argument");
                    alert.setHeaderText("");
                    alert.setContentText("Number of clusters should be positive & less then size of dataSet");
                    alert.show();
                }
            } else {
                canvas.getGraphicsContext2D().setFill(Color.BLUEVIOLET);
                kMeans.step();
                canvas.eraseCenters(kMeans.getCenters());
                kMeans.moveCenters();
                canvas.drawClusters(kMeans.getCenters(), kMeans.getClusters());

            }
        });

        //Если алгоритм уже запущен, вывести готовые кластеры
        tool.getStepButton().setOnAction(e -> {
            canvas.eraseCenters(kMeans.getCenters());
            kMeans.step();
            while (kMeans.moveCenters()){
                kMeans.step();
            }
            canvas.eraseCenters(kMeans.getCenters());
            canvas.drawClusters(kMeans.getClusters());
            kMeans = null;
            tool.reset();
        });

        mainMenu.getMenuRandom().setOnAction(e ->{
            clear(set.getPoints());
            set.getPoints().addAll(new PointSet(2).getPoints());
            canvas.drawPoints(set.getPoints());
        });

        mainMenu.getMenuAbout().setOnAction(e ->{
            WebPageWindow about = new WebPageWindow(true, "src/resources/about.html", "About", 400, 400);
            about.getWebStage().show();
        });
    }

    private void clear(List<Point> set) {
        canvas.getGraphicsContext2D().clearRect(0,0,600,600);
        set.clear();
        canvas.initDraw();
        kMeans = null;
        tool.reset();

    }

}
