package utils;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUtils {
    public static String stringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                System.out.flush();
            } else break;
        }

        return input;
    }

    public static int intInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int input = 0;

        while (true) {
            System.out.print(prompt);

            try {
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid value.");
            }
        }

        return input;
    }

    public static double doubleInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        double input = 0.0;

        while (true) {
            System.out.print(prompt);

            try {
                input = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid value.");
            }
        }

        return input;
    }

    public static void waitForAnyKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        scanner.next();
    }
}