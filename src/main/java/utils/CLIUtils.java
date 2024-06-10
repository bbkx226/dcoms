package utils;

public class CLIUtils {
    private int width = 60; // default width

    public CLIUtils(int width) {
        this.width = width;
    }

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

    public void line() {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println(); // Move to the next line after printing the dashes
    }

    public void printHeader(String header) {
        int paddingSize = (width - header.length()) / 2;
        String padding = " ".repeat((Math.max(0, paddingSize)));
        System.out.println(padding + header + padding);
    }
}
