package utils;

public class MenuTableUtils {

    public void line(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println(); // Move to the next line after printing the dashes
    }

    public void printHeader(int width, String header) {
        int paddingSize = (width - header.length()) / 2;
        String padding = " ".repeat((Math.max(0, paddingSize)));
        System.out.println(padding + header + padding);
    }

}
