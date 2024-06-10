package client.pages;

import client.components.Menu;
import models.*;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class AdminInterface {
    private final Menu menu;

    public AdminInterface(User user) {
        List<String> options = List.of("View User Details", "View Menu", "Check Order", "Order", "Back");
        this.menu = new Menu("Welcome to McGee, " + user.getLastName() + "!!!", options, "Enter your choice:", 60);
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        Scanner scanner = new Scanner(System.in); // Fix typo: System.io should be System.in

        while (true) {
            UIUtils.clrscr();
            menu.display();

            switch (menu.getInput()) {
                case 1:
                    new UserManagementInterface().start();
                    break;
                case 2:
                    new FoodManagementInterface().start();
                    break;
                case 3:
                    new OrderManagementInterface().start();
                    break;
                case 4:
                    // Implement order() method
                    break;
                case 5: // Back option
                    return; // Return to the previous menu or perform any desired action
                default:
                    System.out.println("\nInvalid input. Please try again.");
            }
        }
    }

}
