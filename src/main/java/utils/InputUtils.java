package utils;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUtils {
    public static String stringInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Please enter a value!");
            scanner.next();
        }
        return null;
    }

    public static Integer intInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number!");
        } catch (NoSuchElementException e) {
            System.out.println("Please enter a value!");
        }
        scanner.next();
        return null;
    }

    public static Double doubleInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number!");
        } catch (NoSuchElementException e) {
            System.out.println("Please enter a value!");
        }
        scanner.next();
        return null;
    }
}
