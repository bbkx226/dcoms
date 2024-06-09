package models;

import utils.MenuTableUtils;

import java.util.List;
import java.util.Scanner;

public class Table {
    private String header;
    private List<String> options;
    private List<Integer> optionsID;
    private String prompt;
    private String exitOption;

    public Table(String header, List<String> options, List<Integer> optionsID, String prompt, String exitOption) {
        this.header = header;
        this.options = options;
        this.optionsID = optionsID;
        this.prompt = prompt;
        this.exitOption = exitOption;
    }

    public int displayTable(List<String[]> rows, String[] titles) {
        int[] columnWidths = getColumnWidths(rows, titles);
        int totalWidth = getTotalWidth(columnWidths);
        Scanner scanner = new Scanner(System.in);
        MenuTableUtils menuTableUtils = new MenuTableUtils();

        menuTableUtils.line(totalWidth);
        menuTableUtils.printHeader(totalWidth, header);
        menuTableUtils.line(totalWidth);
        printRow(titles, columnWidths);
        menuTableUtils.line(totalWidth);
        // Check if there are no rows to display (data)
        if (rows.isEmpty()) {
            printCenteredMessage(totalWidth, "No data available");
        } else {
            for (String[] row : rows) {
                printRow(row, columnWidths);
            }
        }
        menuTableUtils.line(totalWidth);


        if (options != null && !options.isEmpty()) {
            for (int i = 0; i < options.size(); i++) { //print optionID
                System.out.println(i + 1 + ". " + options.get(i));
            }
            if (exitOption != null && !exitOption.isEmpty()) {
                System.out.println("0. " + exitOption);
            }
            menuTableUtils.line(totalWidth);
            System.out.print(prompt + " ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            if (exitOption != null && choice == 0) {
                return 0;  // Indicate exit
            } else if (choice > 0 && choice <= options.size()) {
                return choice;  // Return the chosen option
            } else {
                System.out.println("\nInvalid choice, please try again.");
                return displayTable(rows, titles);  // Retry if invalid input
            }
        } else if (prompt != "") {
//            menuTableUtils.line(totalWidth);
            System.out.print(prompt + " ");
        }
        return -1;
    }

    private int[] getColumnWidths(List<String[]> rows, String[] titles) {
        int[] columnWidths = new int[titles.length];
        for (int i = 0; i < titles.length; i++) {
            columnWidths[i] = titles[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }
        return columnWidths;
    }

    private int getTotalWidth(int[] columnWidths) {
        int totalWidth = 2;
        for (int width : columnWidths) {
            totalWidth += width + 3; // Adding 3 for padding and separator
        }
        return totalWidth;
    }

    private void printRow(String[] row, int[] columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        for (int i = 0; i < row.length; i++) {
            sb.append(String.format("%-" +  columnWidths[i] + "s | ", row[i]));
        }
        System.out.println(sb.toString());
    }

    private void printCenteredMessage(int width, String message) {
        int paddingSize = ((width - message.length()) / 2) - 1; // -1 because the "|" have 2
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("|" + padding + message + padding + "|");
    }
}
