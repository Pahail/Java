import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    public static void writeLines(File destFile, List<String> list) throws Exception {
        if(destFile == null) {
            System.out.println("Expected not null file");
            throw   new IllegalArgumentException();
        }
        if(destFile.isDirectory()) {
            System.out.println("Expected file, not Directory");
            throw   new IllegalArgumentException();
        }
        if(destFile.exists() && !destFile.canWrite()) {
            System.out.println("No write permission");
            throw  new AccessDeniedException(destFile.getPath());
        }
        if(!destFile.exists()) {
            try {
                if (!destFile.createNewFile()) {
                    System.out.println("File doesn't exist and can't be created");
                    // Возможно в данной деректории запрещено создание файлов
                    // Ну или лубая другая причина не дает создать файл
                }
            } catch (Exception ex) {
                System.out.println("File doesn't exist and can't be created");
                throw ex;
            }
        }

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destFile)) ) {
            if(list != null) {
                for (String s : list) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException ex) {
            System.out.println("Some I/O problem");
            throw ex;
        }

    }

    public static void writeLines(String path, List<String> list) throws Exception {
        if( path == null) {
            System.out.println("Path can't be null");
            throw new IllegalArgumentException();
        }
        File file = new File(path);
        writeLines(file, list);
    }

    public static boolean deleteFile(File target) throws Exception {
        if(target == null ) {
            System.out.println("Expected not null file");
            throw new IllegalArgumentException();
        }
        if(target.isDirectory()){
            System.out.println("Expected file, not Directory");
            throw new IllegalArgumentException();
        }
        if(target.exists() ) {
            if(!target.canWrite()) {
                System.out.println("No permission");
                throw new AccessDeniedException(target.getPath());
            }
            return target.delete();
        } else {
            System.out.println("File doesn't exist");
            return false;
        }
    }

    public static boolean deleteFile(String path) throws Exception {
        if( path == null) {
            System.out.println("Path can't be null");
            throw new IllegalArgumentException();
        }
        File target = new File(path);
        return FileUtils.deleteFile(target);
    }

    public static List<String> readLines(File sourceFile) throws Exception {
        List<String> list = new ArrayList<>();
        if(sourceFile == null) {
            System.out.println("Expected not null file");
            throw new IllegalArgumentException();
        }
        if(!sourceFile.exists()) {
            System.out.println("File doesn't exist");
            return null;
        }
        if(sourceFile.isDirectory()) {
            System.out.println("Expected file, not Directory");
            throw new IllegalArgumentException();
        }
        if(!sourceFile.canRead()) {
            System.out.println("No permission");
            throw new AccessDeniedException(sourceFile.getPath());
        }
        String temp;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile)) ) {
            while( (temp = bufferedReader.readLine() ) != null ) {
                list.add(temp);
            }
        } catch (Exception ex) {
            System.out.println("Reading error");
            throw new IOException(ex);
        }

        return list;
    }

    public static List<String> readLines(String path) throws Exception {
        if( path == null) {
            System.out.println("Path can't be null");
            throw new IllegalArgumentException();
        }
        File sourceFile = new File(path);
        return readLines(sourceFile);
    }

    public static void copyFile(File sourceFile, File destFile) throws Exception {
        writeLines(destFile, readLines(sourceFile));
        try {
            setPermission(destFile, sourceFile.canRead(), sourceFile.canWrite(), sourceFile.canExecute() );
        } catch (Exception ex) {
            System.out.println("Can't set right permissions");
            throw ex;
        }
    }

    public static void copyFile(String sourceFilePath, String destFilePath) throws Exception {
        if(sourceFilePath == null || destFilePath == null ) {
            System.out.println("Path can't be null");
            throw new IllegalArgumentException();
        }
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        writeLines(destFile, readLines(sourceFile));
    }

    public static void setPermission(File target, boolean readable, boolean writable, boolean executable) throws Exception {
       if(target == null) {
           System.out.println("Expected not null file");
           throw new IllegalArgumentException();
       }
        try {
            if(!target.setReadable(readable) ) {
                System.out.println("Can't set Readable");
                throw new RuntimeException();
            }
            if(!target.setWritable(writable) ) {
                System.out.println("Can't set Writable");
                throw new RuntimeException();
            }
            if(!target.setExecutable(executable) ) {
                System.out.println("Can't set Executable");
                throw new RuntimeException();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }



}
