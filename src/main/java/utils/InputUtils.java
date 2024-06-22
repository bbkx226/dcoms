package utils;

import java.util.Scanner;

public class InputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static String stringInput(String prompt, String cancelString) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase(cancelString)) {
                return null; // Return null to indicate cancellation
            }

            if (!userInput.isEmpty()) {
                return userInput;
            }

            System.out.println("\nInvalid input. Please enter a valid value or '" + cancelString + "' to cancel.");
        }
    }

    public static int intInput(String prompt, String cancelString) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase(cancelString)) {
                return Integer.MIN_VALUE; // Return a special value to indicate cancellation
            }

            try {
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid value or '" + cancelString + "' to cancel.");
            }
        }
    }

    public static char charInput(String prompt, char cancelChar) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();

            if (userInput.length() == 1 && userInput.charAt(0) == cancelChar) {
                return '\0'; // Return null character to indicate cancellation
            }

            if (userInput.length() == 1) {
                return userInput.charAt(0);
            }

            System.out.println("\nInvalid input. Please enter a single character or '" + cancelChar + "' to cancel.");
        }
    }

    public static double doubleInput(String prompt, String cancelString) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase(cancelString)) {
                return Double.NaN; // Return a special value to indicate cancellation
            }

            try {
                return Double.parseDouble(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid value or '" + cancelString + "' to cancel.");
            }
        }
    }

    public static void waitForAnyKey() {
        System.out.println("\nPress any key to continue...");
        scanner.nextLine();
    }
}