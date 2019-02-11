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

public class TestesForCopyFile {
    private File sourceFile;
    private File destFile;

    @Before
    public void setUp() {
        sourceFile = new File("src/test/java/FileUtilsTests/sourceFile");
        try {
            if(!sourceFile.exists() &&!sourceFile.createNewFile() ) {
                throw new RuntimeException();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        destFile = new File("src/test/java/FileUtilsTests/destFile");
        try {
            if(!destFile.exists() && !destFile.createNewFile() ) {
                throw new RuntimeException();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @After
    public void tearDown() {
        if((sourceFile != null) && sourceFile.exists()) {
            if (sourceFile.setReadable(true) && sourceFile.setWritable(true) ) {
                if (!sourceFile.delete()) {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        }
        if((destFile != null) && destFile.exists()) {
            if (destFile.setReadable(true) && destFile.setWritable(true) ) {
                if (!destFile.delete()) {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Test
    public void copyFileCorrect() throws Exception{
        List<String> testStringList = new ArrayList<>();
        testStringList.add("Walk away from the sun, come slowly undone");
        testStringList.add("I can see in your eyes I've already won");
        testStringList.add("I could bleed for a smile, I could cry for some fun");
        testStringList.add("Walk away from the sun, and tell everyone");
        FileUtils.writeLines(sourceFile, testStringList);
        FileUtils.copyFile(sourceFile, destFile);
        assertEquals(testStringList, FileUtils.readLines(destFile));
    }

    @Test
    public void copyFileWithPermissionCorrect() throws Exception{
        FileUtils.writeLines(sourceFile, null);
        FileUtils.setPermission(sourceFile, true, false, false);
        FileUtils.copyFile(sourceFile, destFile);
        assertTrue("Error with setting permission", destFile.canRead());
        assertFalse("Error with setting permission", destFile.canWrite());
        assertFalse("Error with setting permission", destFile.canExecute());

    }

    @Test(expected = AccessDeniedException.class)
    public void copyFileWithUnreadableSource() throws Exception{
        FileUtils.writeLines(sourceFile, null);
        FileUtils.setPermission(sourceFile, false, false, false);
        FileUtils.copyFile(sourceFile, destFile);
    }


}
