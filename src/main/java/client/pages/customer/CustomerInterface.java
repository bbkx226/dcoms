package client.pages.customer;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.CustomerActions;
import client.FoodActions;
import client.components.Menu;
import models.User;
import utils.InputUtils;
import utils.UIUtils;

public class CustomerInterface {
    private final Menu menu;
    private final User user;

    public CustomerInterface(User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        List<String> options = List.of("View Menu", "Order", "Checkout", "Logout");
        this.menu = new Menu(" Welcome to McGee, " + fullName + "!", options);
        this.user = user;
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        while (true) {
            UIUtils.clearScreen();
            menu.display();

            switch (menu.getInput("Enter your choice: ")) {
                case 1:
                    FoodActions.displayFoods();
                    InputUtils.waitForAnyKey();
                    break;
                case 2:
                    new OrderInterface(this.user).start(); //Order, Update, Delete Food
                    break;
                case 3:
                    new CustomerActions(this.user).checkOrder(); // Check orders in cart & proceed to payment
                    break;
                case 4:
                    System.out.println("Logged out.");
                    return; // Return to the previous menu
            }
        }
    }
}
