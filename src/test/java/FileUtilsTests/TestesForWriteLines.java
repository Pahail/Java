package FileUtilsTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;


public class TestesForWriteLines {

    private File testFile;
    private File readonly;

    @Before
    public void setUp() {
        readonly = new File("src/test/java/FileUtilsTests/readonly");
        try {
            if(!readonly.createNewFile() ) {
                throw new RuntimeException();
            }
            if(!readonly.setReadOnly() ) {
                throw new RuntimeException();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @After
    public void tearDown() {
        if(readonly != null && readonly.exists() ) {
            if (readonly.setWritable(true) ) {
                if (!readonly.delete()) {
                    throw new RuntimeException();
                }
             } else {
                throw new RuntimeException();
            }
        }
        readonly = null;
        if(testFile != null && testFile.exists() ) {
            if(!testFile.delete()) {
                throw  new RuntimeException();
            }
        }
        testFile = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeLinesWithNullPathString() throws Exception {
        FileUtils.writeLines((String)null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeLinesWithNullDestinationFile() throws Exception {
        FileUtils.writeLines((File)null, null);
    }

    @Test
    public void writeLinesCorrect() throws Exception{
        testFile = new File("src/test/java/FileUtilsTests/testFile");
        List<String> testStringList = new ArrayList<>();
        testStringList.add("Walk away from the sun, come slowly undone");
        testStringList.add("I can see in your eyes I've already won");
        testStringList.add("I could bleed for a smile, I could cry for some fun");
        testStringList.add("Walk away from the sun, and tell everyone");
        FileUtils.writeLines(testFile, testStringList);
        //TODO Напишу readLines, протестирую его и буду использовать здесь
        //Глупо получилось, ибо при тестировании readLines я пользуюсь writeLines
        assertEquals(testStringList, FileUtils.readLines(testFile));

    }

    @Test(expected = AccessDeniedException.class)
    public void writeLinesIntoReadOnlyFile()  throws Exception{
        FileUtils.writeLines(readonly, null);
    }
}
