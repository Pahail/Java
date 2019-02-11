package pahail.gRaph.main.parse;

import javafx.scene.control.Alert;
import pahail.gRaph.main.core.Series;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static List<Double> parseLine(String input) throws Exception{
        List<Double> doubleList = new ArrayList<>();
        String[] numbers = input.split(" ");
        for (String s: numbers) {
            try {
                doubleList.add(Double.parseDouble(s));
            } catch (Exception e) {
                System.err.println("Can't parse string line to double");
                throw e;
            }
        }
        return doubleList;
    }

    public static List<Series> parseDataFile(List<String> input) {
        List<Series> seriesList = new ArrayList<>();
        if(input.size() % 2 != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't parse this file");
            alert.setHeaderText(null);
            alert.setContentText("Expected even number of rows\n" +
                    "Please check or choose another file");
            alert.showAndWait();
            System.err.println("Can't create series: Expected even number of rows");
            return null;
        }
        List<Double> first, second;
        for(int i = 0; i < input.size(); i+=2 ) {
            try{
                first = parseLine(input.get(i));
                second = parseLine(input.get(i+1));
            } catch (Exception e) {
                //TODO Сообщить пользователю - лучше не сообщать, а то заспамят ошибки)
                System.err.println("Can't create series: can't parse to double");
                seriesList.add(null);
                continue;
            }
            seriesList.add(new Series(first, second));
        }
        return seriesList;
    }
}
