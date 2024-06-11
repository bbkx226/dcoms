package client.components;

import utils.InputUtils;
import utils.UIUtils;

import java.util.List;

public class Menu {
    private final String header;
    private final List<String> options;
    private final String prompt;
    private final int width;
    private UIUtils uiUtils;

    public Menu(String header, List<String> options, String prompt, int width) {
        this.header = header;
        this.options = options;
        this.prompt = prompt;
        this.width = width;

    }

    public Menu(String header, List<String> options, String prompt) {
        this.header = header;
        this.options = options;
        this.prompt = prompt;
        this.width = UIUtils.defaultWidth;
    }

    public void display() {
        // if header is not empty, print header with format
        if (!header.isEmpty()){
            UIUtils.line(width);
            UIUtils.printHeader(header, width);
            UIUtils.line(width);
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }

        UIUtils.line(width);
    }

    public int getInput() {
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
