import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> testStringList = new ArrayList<String>();
        testStringList.add("Walk away from the sun, come slowly undone");
        testStringList.add("I can see in your eyes I've already won");
        testStringList.add("I could bleed for a smile, I could cry for some fun");
        testStringList.add("Walk away from the sun, and tell everyone");
        FileUtils.writeLines("src/main/resources/test.txt", testStringList);
    }
}
