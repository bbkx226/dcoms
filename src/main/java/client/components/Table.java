package client.components;

import java.util.List;

import utils.UIUtils;

public class Table {
    private final String header;
    private final String[] headers;
    private final List<String[]> rows;
    private final int[] columnWidths;
    private final int totalWidth;

    public Table(String header, String[] headers, List<String[]> rows) {
        this.header = header;
        this.headers = headers;
        this.rows = rows;

        this.columnWidths = getColumnWidths(rows, headers);
        this.totalWidth = calcTotalWidth(columnWidths);
    }

    // display table's headers and data
    public void display() {
        UIUtils.printLine(totalWidth);
        UIUtils.printHeader(header, totalWidth);
        UIUtils.printLine(totalWidth);
        printRow(headers,columnWidths);
        UIUtils.printLine(totalWidth);
        // Check if there are no rows to display (data)
        if (rows.isEmpty()) {
            printCenteredMessage(totalWidth, "No data available");
        } else {
            for (String[] row : rows) {
                printRow(row, columnWidths);
            }
        }
        UIUtils.printLine(totalWidth);
    }

    private int[] getColumnWidths(List<String[]> rows, String[] headers) {
        int[] widths = new int[headers.length]; // Renamed variable for clarity
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }
    
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }
        return widths;
    }

    private int calcTotalWidth(int[] columnWidths) {
        int calculatedTotalWidth = 2; // Renamed variable to avoid hiding
        for (int width : columnWidths) {
            calculatedTotalWidth += width + 3; // Adding 3 for padding and separator
        }
        return calculatedTotalWidth;
    }

    private void printRow(String[] row, int[] columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        for (int i = 0; i < row.length; i++) {
            sb.append(String.format("%-" +  columnWidths[i] + "s | ", row[i]));
        }
        System.out.println(sb);
    }

    private void printCenteredMessage(int messageWidth, String message) {
        int paddingSize = ((messageWidth - message.length()) / 2) - 1; // -1 because the "|" have 2
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("|" + padding + message + padding + "|");
    }
}
