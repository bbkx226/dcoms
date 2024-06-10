package client.components;

import utils.UIUtils;

import java.util.List;
import java.util.Scanner;

public class Table {
    private final String header;
    private final String[] headers;
    private final List<String[]> rows;

    public Table(String header, String[] headers, List<String[]> rows) {
        this.header = header;
        this.headers = headers;
        this.rows = rows;
    }

    // display table's headers and data
    public void display() {
        int[] columnWidths = getColumnWidths(rows, headers);
        int totalWidth = getTotalWidth(columnWidths);
        Scanner scanner = new Scanner(System.in);

        UIUtils.line(totalWidth);
        UIUtils.printHeader(header, totalWidth);
        UIUtils.line(totalWidth);
        printRow(headers, columnWidths);
        UIUtils.line(totalWidth);
        // Check if there are no rows to display (data)
        if (rows.isEmpty()) {
            printCenteredMessage(totalWidth, "No data available");
        } else {
            for (String[] row : rows) {
                printRow(row, columnWidths);
            }
        }
        UIUtils.line(totalWidth);
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
