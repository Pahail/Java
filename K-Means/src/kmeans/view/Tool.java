package kmeans.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

public class Tool extends HBox {

    private NumberTextField numberTextField;
    private Button startButton;
    private Button skipButton;

    public int getNumber() {
        try {
            return Integer.parseInt(numberTextField.getText());
        } catch (Exception e) {
            return -1;
        }
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStepButton() {
        return skipButton;
    }

    public Tool() {
        setSpacing(5.0);
        Label text1 = new Label("Enter number of clusters");
        text1.setPadding(new Insets(7, 10,3,10));
        getChildren().add(text1);

        numberTextField = new NumberTextField();
        numberTextField.setText("0");
        numberTextField.setMaxWidth(40.0);
        numberTextField.setTranslateY(2.5);
        numberTextField.setFocusTraversable(false);
        setMaxHeight(50.0);
        setPrefHeight(34.0);
        setMaxWidth(320.0);
        setStyle("-fx-border-width: 1pt; -fx-border-color: gray; -fx-background-color: whitesmoke; -fx-border-radius: 8pt");
        getChildren().add(numberTextField);

        SVGPath triangle = new SVGPath();
        String triangleRight = "M 10 10 L 30 20 L 10 30 z";
        SVGPath moveForward = new SVGPath();
        String triangleSkip = "M 10 10 L 10 30 L 20 20 L 20 30 L 30 20 L 20 10 L 20 20 L 10 10  z";
        triangle.setContent(triangleRight);
        moveForward.setContent(triangleSkip);
        startButton = new Button("", triangle);
        skipButton = new Button("", moveForward);
        skipButton.setDisable(true);

        skipButton.setStyle("-fx-border-width: 1pt; -fx-border-color: gray; -fx-background-radius: 0 10 10 0;  -fx-border-radius: 0 10 10 0");
        startButton.setStyle("-fx-border-width: 1pt; -fx-border-color: gray;");


        startButton.setTooltip(new Tooltip("Start K-Means algorithm"));
        skipButton.setTooltip(new Tooltip("Run algorithm and show final result"));
        startButton.setAlignment(Pos.CENTER_RIGHT);
        skipButton.setAlignment(Pos.CENTER_RIGHT);
        startButton.setTranslateY(1.0);
        skipButton.setTranslateY(1.0);
        skipButton.setPrefWidth(40.0);
        skipButton.setMaxWidth(40.0);
        skipButton.setMinWidth(40.0);
        startButton.setPrefWidth(40.0);
        startButton.setMaxWidth(40.0);
        startButton.setMinWidth(40.0);

        skipButton.setPrefHeight(30.0);
        skipButton.setMaxHeight(30.0);
        skipButton.setMinHeight(30.0);
        startButton.setPrefHeight(30.0);
        startButton.setMaxHeight(30.0);
        startButton.setMinHeight(30.0);

        startButton.setOnAction(e -> {
            System.out.println(getNumber());
        });
        getChildren().add(startButton);
        getChildren().add(skipButton);

    }

    public void reset() {
        numberTextField.setText("0");
        numberTextField.setTooltip(new Tooltip("0"));
        startButton.setTooltip(new Tooltip("Start K-Means algorithm"));
        skipButton.setDisable(true);

    }
}
