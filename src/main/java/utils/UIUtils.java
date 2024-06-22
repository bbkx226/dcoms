package utils;

public class UIUtils {
    public static final int DEFAULT_WIDTH = 60; // Default width constant

    // Clears the screen by printing new lines
    public static void clearScreen() {
        final int linesToClear = 100;
        for (int i = 0; i < linesToClear; i++) System.out.println();
    }

    // Overloaded implementation of line()
    public static void printLine(int width) {
        String line = "-".repeat(width);
        System.out.println(line);
    }

    // Prints a header centered within the specified width
    public static void printHeader(String header, int width) {
        int paddingSize = (width - header.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println(padding + header + padding);
    }
}
