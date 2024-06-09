package models;

import utils.MenuTableUtils;

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
        MenuTableUtils menuTableUtils = new MenuTableUtils();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            menuTableUtils.line(width);
            menuTableUtils.printHeader(width, header);
            menuTableUtils.line(width);
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            // TODO: Solve this minor error while user input 0 to exit
            if (!exitOption.equals("")) {
                System.out.println("0. " + exitOption);
            }

            menuTableUtils.line(width);
            System.out.print(prompt + " ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            // avoid the exit or back error
            if (exitOption.equals("") && choice == 0) {
                return choice;
            }

            if (choice == 0) {
                exit = true;
            } else if (choice > 0 && choice <= options.size()) {
                System.out.println("You selected: " + options.get(choice - 1));
                return choice;
                // Add logic to handle the chosen option here
            } else {
                System.out.println("\nInvalid choice, please try again.");
            }
        }

        System.out.println("Exiting the menu. Goodbye!");
        scanner.close();
        return 0;
    }

}
