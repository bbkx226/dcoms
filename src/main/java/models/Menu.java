package models;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private String header;
    private List<String> options;
    private String prompt;
    private String exitOption;
    private int width;

    public Menu(String header, List<String> options, String prompt, String exitOption, int width) {
        this.header = header;
        this.options = options;
        this.prompt = prompt;
        this.exitOption = exitOption;
        this.width = width;
    }

    public int display() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            line(width);
            printHeader(width, header);
            line(width);
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            // TODO: Solve this minor error while user input 0 to exit
            if (!exitOption.equals("")) {
                System.out.println("0. " + exitOption);
            }

            line(width);
            System.out.print(prompt + " ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                exit = true;
            } else if (choice > 0 && choice <= options.size()) {
                System.out.println("You selected: " + options.get(choice - 1));
                return choice;
                // Add logic to handle the chosen option here
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }

        System.out.println("Exiting the menu. Goodbye!");
        scanner.close();
        return 0;
    }

    private void line(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println(); // Move to the next line after printing the dashes
    }

    private void printHeader(int width, String header) {
        int paddingSize = (width - header.length()) / 2;
        String padding = " ".repeat((Math.max(0, paddingSize)));
        System.out.println(padding + header + padding);
    }
}
