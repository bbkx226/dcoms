package utils;

public class UIUtils {
    public static int defaultWidth = 60; // default width

    public static void clrscr() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    // Overloaded implementation of line()
    public static void line(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println(); // Move to the next line after printing the dashes
    }

    // Overloaded implementation of printHeader()
    public static void printHeader(String header, int width) {
        int paddingSize = (width - header.length()) / 2;
        String padding = " ".repeat((Math.max(0, paddingSize)));
        System.out.println(padding + header + padding);
    }
}
