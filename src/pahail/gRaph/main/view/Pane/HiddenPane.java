package pahail.gRaph.main.view.Pane;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

/*Раскрывающееся/сворачивающееся панель
 */

public class HiddenPane extends VBox {
    private HBox title;             // Верхняя часть для заголовка
    private HBox strip;             // Нижняя часть - кнопка сворачивания
    private HBox field;             // Часть с телом окна
    private VBox body;              // Контенер для контента
    private boolean deployed;       // Булевское состояние панели

    public void setTitle(String title) {
        Label label = new Label(title);
        this.title.getChildren().add(label);
    }

    public HiddenPane(boolean init) {
        title = new HBox();
        field = new HBox();
        body = new VBox();
        strip = new HBox();
        deployed = false;
        title.setStyle("-fx-border-color: grey;");
        field.setStyle("-fx-border-color: grey;");
        strip.setStyle("-fx-border-color: grey; -fx-background-color: grey;");
        VBox.setMargin(this, new Insets(5, 10, 5, 10));
        title.setAlignment(Pos.CENTER);
        field.setAlignment(Pos.CENTER);
        field.setVisible(false);
        strip.setAlignment(Pos.CENTER);
        SVGPath triangle = new SVGPath();
        String triangleDown = "M 10 10 L 30 10 L 20 20 z";
        String triangleUp = "M 10 10 L 30 10 L 20 0 z";

        triangle.setContent(triangleDown);
        strip.getChildren().add(triangle);
        HBox.setMargin(triangle, new Insets(2,0,2,0));

        if(init) {
            field.getChildren().add(body);
            triangle.setContent(triangleUp);
            field.setVisible(true);
            deployed = true;
        }
        super.getChildren().addAll(title, field, strip);
        strip.setPrefHeight(12);


        strip.setOnMousePressed(e -> {
            if(!deployed) {
                field.getChildren().add(body);
                triangle.setContent(triangleUp);
                field.setVisible(true);
                deployed = true;
            }else {
                field.getChildren().remove(body);
                triangle.setContent(triangleDown);
                field.setVisible(false);
                deployed = false;

            }
        });

    }

    public HiddenPane () {
        this(false);
    }

    public ObservableList<Node> getBody() {
        return body.getChildren();
    }


}
