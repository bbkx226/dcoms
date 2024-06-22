package client.pages.admin;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.components.Menu;
import models.User;
import utils.UIUtils;

public class AdminInterface {
    private final Menu menu;

    public AdminInterface(User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        List<String> options = List.of("Manage Users", "Manage Food", "Manage Orders", "Logout");
        this.menu = new Menu("Welcome to McGee, " + fullName + "!", options);
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        while (true) {
            UIUtils.clearScreen();
            menu.display();

            switch (menu.getInput("Enter your choice: ")) {
                case 1 -> UserManagementInterface.start();
                case 2 -> FoodManagementInterface.start();
                case 3 -> OrderManagementInterface.start();
                case 4 -> {
                    System.out.println("Logged out.");
                    return; // Return to the previous menu or perform any desired action
                }
            }
        }
    }
}
