package models;

import utils.MenuTableUtils;

import java.util.List;

public class Table {
    private String header;
    private List<String> options;
    private String prompt;
    private String exitOption;

    public Table(String header, List<String> options, String prompt, String exitOption) {
        this.header = header;
        this.options = options;
        this.prompt = prompt;
        this.exitOption = exitOption;
    }

    public void displayTable(List<String[]> rows, String[] titles, String header) {
        int[] columnWidths = getColumnWidths(rows, titles);
        int totalWidth = getTotalWidth(columnWidths);
        MenuTableUtils menuTableUtils = new MenuTableUtils();

        menuTableUtils.line(totalWidth);
        menuTableUtils.printHeader(totalWidth, header);
        menuTableUtils.line(totalWidth);
        printRow(titles, columnWidths);
        menuTableUtils.line(totalWidth);
        for (String[] row : rows) {
            printRow(row, columnWidths);
        }
        menuTableUtils.line(totalWidth);
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


}
