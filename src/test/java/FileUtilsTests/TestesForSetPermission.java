package FileUtilsTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestesForSetPermission {
    private File testFile;

    @Before
    public void setUp() {
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
        if((testFile != null) && testFile.exists()) {
            if (testFile.setReadable(true) && testFile.setWritable(true) ) {
                if (!testFile.delete()) {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Test
    public void setPermissionCorrect() throws Exception {
        Random random = new Random();
        boolean readable = random.nextBoolean();
        boolean writable = random.nextBoolean();
        boolean executable = random.nextBoolean();
        FileUtils.setPermission(testFile, readable, writable, executable);
        assertEquals("Error with setting permission", readable, testFile.canRead() );
        assertEquals("Error with setting permission", writable, testFile.canWrite() );
        assertEquals("Error with setting permission", executable, testFile.canExecute());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPermissionWithNullFile() throws Exception {
        FileUtils.setPermission((File)null, true, true, true);
    }



}
