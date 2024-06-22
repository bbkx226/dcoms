package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    private static final Path PROJECT_FILE_PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "databases");
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    public enum FileType {
        USER(PROJECT_FILE_PATH.resolve("users.txt")),
        FOOD(PROJECT_FILE_PATH.resolve("foods.txt"));

        private final Path filePath;

        FileType(Path filePath) {
            this.filePath = filePath;
        }

        public Path getFilePath() {
            return filePath;
        }
    }

    // Appends a string representation of an item to a file
    public static <T> void appendToFile(FileType fileType, T item, Function<T, String> toStringFunction) {
        try {
            Files.writeString(fileType.getFilePath(), toStringFunction.apply(item) + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error appending to file", e);
        }
    }

    // Reads from a file and converts each line to an item of type T
    public static <T> List<T> readFromFile(FileType fileType, Function<String, T> fromStringFunction) {
        try (Stream<String> lines = Files.lines(fileType.getFilePath())) {
            return lines.map(fromStringFunction).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading from file", e);
            return new ArrayList<>();
        }
    }

    // Writes a list of items to a file, replacing its current content
    public static <T> void updateFile(FileType fileType, List<T> items, Function<T, String> toStringFunction) {
        try {
            Files.write(fileType.getFilePath(), items.stream().map(toStringFunction).collect(Collectors.toList()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error updating file", e);
        }
    }

    // Deletes a line from a file at a given index
    public static void deleteFromFile(FileType fileType, int id) {
        List<String> lines;
        try {
            lines = Files.readAllLines(fileType.getFilePath());
            boolean removed = lines.removeIf(line -> matchId(line, id));
            if (removed) {
                Files.write(fileType.getFilePath(), lines);
                LOGGER.log(Level.INFO, "Item with ID {0} deleted successfully.", id);
            } else {
                LOGGER.log(Level.WARNING, "Item with ID {0} not found.", id);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error deleting from file", e);
        }
    }

    private static boolean matchId(String line, int id) {
        String[] parts = line.split(",");
        return parts.length > 0 && parts[0].trim().equals(String.valueOf(id));
    }
}