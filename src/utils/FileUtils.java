package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// CRUD functions for text files
public class FileUtils {
    private static final String PROJECT_FILE_PATH = System.getProperty("user.dir") + "/src/databases/";

    public enum FileType {
        USER(PROJECT_FILE_PATH + "users.txt"),
        FOOD(PROJECT_FILE_PATH + "foods.txt");

        private final String filePath;

        FileType(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    public static <T> void appendToFile(FileType fileType, T item, Function<T, String> toStringFunction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileType.getFilePath(), true))) {
            bw.write(toStringFunction.apply(item));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> readFromFile(FileType fileType, Function<String, T> fromStringFunction) {
        List<T> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileType.getFilePath()))){
            String line;
            while ((line = br.readLine()) != null) {
                items.add(fromStringFunction.apply(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static <T> void updateFile(FileType fileType, List<T> items, Function<T, String> toStringFunction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileType.getFilePath()))) {
            for (T item : items) {
                bw.write(toStringFunction.apply(item));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromFile(FileType fileType, int index) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileType.getFilePath()))) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileType.getFilePath()));

            String line;
            int currentIndex = 0;
            while ((line = br.readLine()) != null) {
                if (currentIndex != index) {
                    bw.write(line);
                    bw.newLine();
                }
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file
        File originalFile = new File(fileType.getFilePath());
        File tempFile = new File(fileType.getFilePath() + ".tmp");
        if (tempFile.renameTo(originalFile)) {
            System.out.println("File deleted successfully.");
        } else {
            System.err.println("Failed to delete file");
        }
    }
}
