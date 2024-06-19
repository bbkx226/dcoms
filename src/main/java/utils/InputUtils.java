package utils;

import java.util.Scanner;

public class InputUtils {
    public static String stringInput(String prompt, String cancelString) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(prompt);

            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase(cancelString)) {
                return null; // Return null to indicate cancellation
            }

            if (!userInput.isEmpty()) {
                return userInput;
            }

            System.out.println("Invalid input. Please enter a valid value or '" + cancelString + "' to cancel.");
        }
    }

    public static int intInput(String prompt, String cancelString) {
        Scanner scanner = new Scanner(System.in);
        int input = 0;

        while (true) {
            System.out.print(prompt);

            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase(cancelString)) {
                return Integer.MIN_VALUE; // Return a special value to indicate cancellation
            }

            try {
                input = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid value or '" + cancelString + "' to cancel.");
            }
        }

        return input;
    }

    public static char charInput(String prompt, char cancelChar) {
        Scanner scanner = new Scanner(System.in);
        char input = '\0';

        while (true) {
            System.out.print(prompt);

            String userInput = scanner.nextLine();
            if (userInput.length() == 1 && userInput.charAt(0) == cancelChar) {
                return '\0'; // Return null character to indicate cancellation
            }

            if (userInput.length() != 1) {
                System.out.println("Invalid input. Please enter a single character or '" + cancelChar + "' to cancel.");
                continue;
            }

            input = userInput.charAt(0);
            break;
        }

        return input;
    }

    public static double doubleInput(String prompt, String cancelString) {
        Scanner scanner = new Scanner(System.in);
        double input = 0;

        while (true) {
            System.out.print(prompt);

            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase(cancelString)) {
                return Double.NaN; // Return a special value to indicate cancellation
            }

            try {
                input = Double.parseDouble(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid value or '" + cancelString + "' to cancel.");
            }
        }

        return input;
    }

    public static void waitForAnyKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }
}