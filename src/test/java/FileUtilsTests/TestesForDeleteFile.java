package FileUtilsTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class TestesForDeleteFile {
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
        testFile = new File("src/test/java/FileUtilsTests/testFile");
        try {
            if(!testFile.createNewFile() ) {
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


    @Test
    public void deleteFile() throws Exception {
        assertTrue("Deleting has failed", FileUtils.deleteFile(testFile));
    }

    @Test
    public void deleteFileWithPathString() throws Exception {
        assertTrue("Deleting has failed", FileUtils.deleteFile(testFile.getPath()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFileWithNullPathString() throws Exception {
        FileUtils.deleteFile( (String)null );
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFileWithNullTargetFile() throws Exception {
        FileUtils.deleteFile( (File)null );
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteReadOnlyFile() throws Exception {
        FileUtils.deleteFile( readonly );
    }

    @Test
    public void deleteNonexistentFile() throws Exception{
        assertFalse("Unexpected return value", FileUtils.deleteFile("src/test/java/FileUtilsTests/nonexistent") );
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFileWhenArgumentIsDirectory() throws Exception {
        FileUtils.deleteFile( "src/test/java/FileUtilsTests" );
    }


}
