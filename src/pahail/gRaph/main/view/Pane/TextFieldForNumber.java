package pahail.gRaph.main.view.Pane;

import javafx.scene.control.TextField;

public class TextFieldForNumber extends TextField {

    private double defaultValue;

    public TextFieldForNumber(double defaultValue ) {
        this.defaultValue = defaultValue;
        setText(defaultValue + "");
        //Только разрешенный формат ввода (Парсящийся к даблу)
        this.textProperty().addListener((observable, oldValue, newValue) -> {

            //Вот такой вот логический кошмар всего лишь чтоб оставить только правильный числовой ввод
            if (newValue != null && newValue.length() > 0 && newValue.charAt(0) == '-') {
                if (!newValue.matches("[0-9.]*")) {
                    setText('-' + newValue.replaceAll("[^\\d^.]", ""));
                }
            } else {
                if (newValue != null && !newValue.matches("[0-9.]*")) {
                    setText(newValue.replaceAll("[^\\d^.]", ""));
                }
            }
            boolean isInteger = true;
            for (int i = 0; (newValue != null) && (i < newValue.length()); i++) {
                if ((newValue.charAt(i) == '.') && !isInteger) {
                    setText(newValue.substring(0, i) + newValue.substring(i + 1));
                }
                if (newValue.charAt(i) == '.' && isInteger) {
                    isInteger = false;
                }
            }

        });
    }

    public TextFieldForNumber() {
        this(0);
    }

    public double getValue() throws NumberFormatException  {
        if(getText().equals("")) {
            setText(defaultValue + "");
            return 0;
        }
        return Double.parseDouble(getText());
    }
}
