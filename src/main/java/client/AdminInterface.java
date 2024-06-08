package client;

import models.Menu;
import utils.ClearScreen;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import models.User;

public class AdminInterface {
    private User user;
    public void adminMenu(User user) {
        this.user = user;
        Scanner scanner = new Scanner(System.in); // Fix typo: System.io should be System.in

        while (true) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            List<String> options = List.of("View User Details", "View Menu", "Check Order", "Order", "Back"); // Add "Back" option
            Menu menu = new Menu(" Welcome to McGee, " + fullName + "!!!", options, "Enter your choice:", "", 60); // Change "back" to "Back"
            try {
                ClearScreen.clrscr();
                int choice = menu.display();

                switch (choice) {
                    case 1:
                        // Implement viewUserDetails() method
                        break;
                    case 2:
                        viewMenu(scanner);
                        break;
                    case 3:
                        // Implement checkOrder() method
                        break;
                    case 4:
                        // Implement order() method
                        break;
                    case 5: // Back option
                        return; // Return to the previous menu or perform any desired action
                    default:
                        System.out.println("Invalid input. Please try again.");
                        scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            }
        }
    }

    private void viewMenu(Scanner scanner) {
        // Implement viewMenu functionality
    }
}
