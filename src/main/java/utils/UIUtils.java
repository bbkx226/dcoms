package utils;

public class UIUtils {
    public static int defaultWidth = 60; // default width

    public static void clrscr() {
//        try{
//            String operatingSystem = System.getProperty("os.name"); //Check the current operating system
//
//            if(operatingSystem.contains("Windows")){
//                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
//                Process startProcess = pb.inheritIO().start();
//                startProcess.waitFor();
//            } else {
//                ProcessBuilder pb = new ProcessBuilder("clear");
//                Process startProcess = pb.inheritIO().start();
//
//                startProcess.waitFor();
//            }
//        }catch(Exception e){
//            System.out.println(e);
//        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
