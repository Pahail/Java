package FileUtilsTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class TestesForReadLines {
    private File testFile;
    private File nonPermission;

    @Before
    public void setUp() {
        nonPermission = new File("src/test/java/FileUtilsTests/nonPermission");
        try {
            if(!nonPermission.createNewFile() ) {

                throw new RuntimeException();
            }
            if(!nonPermission.setReadable(false) ) {
                throw new RuntimeException();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        testFile = new File("src/test/java/FileUtilsTests/testFile");
        try {
            if(!testFile.exists() && !testFile.createNewFile() ) {
                throw new RuntimeException();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @After
    public void tearDown() {
        if((nonPermission != null) && nonPermission.exists()) {
            if (nonPermission.setReadable(true) ) {
                if (!nonPermission.delete()) {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        }
        nonPermission = null;
        if(testFile != null && testFile.exists() ) {
            if(!testFile.delete()) {
                throw  new RuntimeException();
            }
        }
        testFile = null;
    }

    @Test
    public void readLinesCorrect() throws Exception {
        List<String> testStringList = new ArrayList<>();
        testStringList.add("Walk away from the sun, come slowly undone");
        testStringList.add("I can see in your eyes I've already won");
        testStringList.add("I could bleed for a smile, I could cry for some fun");
        testStringList.add("Walk away from the sun, and tell everyone");
        FileUtils.writeLines(testFile, testStringList);
        assertEquals("Reading error", testStringList, FileUtils.readLines(testFile));
    }

    @Test(expected = AccessDeniedException.class)
    public void readLinesWithoutPermission() throws Exception {
        FileUtils.readLines(nonPermission);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLinesWithNullSourceFile() throws Exception {
        FileUtils.readLines( (File)null );
    }

    @Test(expected = IllegalArgumentException.class)

    public void readLinesWithNullPath() throws Exception {
        FileUtils.readLines( (String)null );
    }

    @Test(expected = FileNotFoundException.class)
    public void readLinesWithNonexistentFile() throws Exception {
        FileUtils.readLines( "src/test/java/FileUtilsTests/nonexistent" );
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLinesWithDirectoryName() throws Exception {
        FileUtils.readLines( "src/test/java/FileUtilsTests" );
    }


}
