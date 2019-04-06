package utils;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Double> parseLine(String input) throws Exception{
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

}