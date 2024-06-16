package client.pages;

import client.CustomerActions;
import client.FoodActions;
import client.components.Menu;
import models.User;
import utils.InputUtils;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerInterface {
    private User user;

    public void customerMenu(User user) throws MalformedURLException, NotBoundException, RemoteException {
        this.user = user;
        Scanner scanner = new Scanner(System.in); // Fix typo: System.io should be System.in

        while (true) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            List<String> options = List.of("View Menu", "Order", "Checkout", "Back");
            Menu menu = new Menu(" Welcome to McGee, " + fullName + "!!!", options, "Enter your choice:", 60);
            try {
                UIUtils.clrscr();
                menu.display();

                switch (menu.getInput()) {
                    case 1:
                        FoodActions.viewMenu();
                        break;
                    case 2:
                        new OrderInterface(user).orderMenu(); //Order, Update, Delete Food
                        break;
                    case 3:
                        CustomerActions.checkOrder(user); // Check orders in cart & proceed to payment
                        break;
                    case 4: // Back option
                        return; // Return to the previous menu
                    default:
                        System.out.println("\nInvalid input. Please try again.");
                        InputUtils.waitForAnyKey();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number between 1 and 4.");
                InputUtils.waitForAnyKey();
            }
        }
    }

    private void viewMenu(Scanner scanner) {
        // Implement viewMenu functionality
    }
}
