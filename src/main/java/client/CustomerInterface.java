package client;

import models.Menu;
import models.User;
import utils.ClearScreen;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerInterface {
    private User user;

    public void customerMenu(User user) {
        this.user = user;
        Scanner scanner = new Scanner(System.in); // Fix typo: System.io should be System.in

        while (true) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            List<String> options = List.of("View Menu", "Order", "Check Order", "Back"); // Add "Back" option
            Menu menu = new Menu(" Welcome to McGee, " + fullName + "!!!", options, "Enter your choice:", "", 60); // Change "back" to "Back"
            try {
                ClearScreen.clrscr();
                int choice = menu.display();

                switch (choice) {
                    case 1:
//                        viewMenu(scanner);
                        break;
                    case 2:
//                        orderMenu();
                        break;
                    case 3:
//                        checkOrder();
                        break;
                    case 4: // Back option
                        return; // Return to the previous menu
                    default:
                        System.out.println("Invalid input. Please try again.");
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
