package client.components;

import utils.InputUtils;
import utils.CLIUtils;

import java.util.List;

public class Menu {
    private final String header;
    private List<String> options;
    private final String prompt;
    private final int width;

    public Menu(String header, List<String> options, String prompt, int width) {
        this.header = header;
        this.options = options;
        this.prompt = prompt;
        this.width = width;
    }

    public Menu(String header, String prompt, int width) {
        this.header = header;
        this.prompt = prompt;
        this.width = width;
    }

    public void display() {
        CLIUtils CLIUtils = new CLIUtils(width);

        // if header is not empty, print header with format
        if (!header.isEmpty()){
            CLIUtils.line();
            CLIUtils.printHeader(header);
            CLIUtils.line();
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }

        CLIUtils.line();
    }

    public int getInput() {
        int choice = 0;

        while (true) {
            System.out.print(prompt + " ");

            choice = InputUtils.intInput();
            if (choice < 1 || choice > options.toArray().length) {
                System.out.println("\nInvalid input. Please enter a number between 1 and " + options.toArray().length + ".");
            } else break;
        }
        return choice;
    }
}
