package client.components;

import java.util.List;

import utils.InputUtils;
import utils.UIUtils;

public class Menu {
    private final String header;
    private final List<String> options;
    private final int width;

    // menu with specific width
    public Menu(String header, List<String> options, int width) {
        this.header = header;
        this.options = options;
        this.width = width;
    }

    // menu with default width
    public Menu(String header, List<String> options) {
        this.header = header;
        this.options = options;
        this.width = UIUtils.DEFAULT_WIDTH;
    }

    public void display() {
        // if header is not empty, print header with format
        if (!header.isEmpty()){
            UIUtils.printLine(width);
            UIUtils.printHeader(header, width);
            UIUtils.printLine(width);
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }

        UIUtils.printLine(width);
    }

    public int getInput(String prompt) {
        int choice = 0;

        while (true) {
            choice = InputUtils.intInput(prompt, "b");
            if (choice < 1 || choice > options.toArray().length) {
                System.out.println("\nInvalid input. Please enter a number between 1 and " + options.toArray().length + ".");
            } else break;
        }
        return choice;
    }
}
