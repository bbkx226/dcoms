package client.pages.customer;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.CustomerActions;
import client.OrderActions;
import client.components.Menu;
import models.User;
import utils.UIUtils;

public class OrderInterface {
    private final User user;

    public OrderInterface(User user) {
        this.user = user;
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
       List<String> options = List.of("Place Order", "Update Order", "Delete Order", "Back");
       Menu menu = new Menu("Manage your orders", options, 73);

       while (true) {
           UIUtils.clearScreen();
           OrderActions.displayUserOrders(this.user);
           CustomerActions ca = new CustomerActions(this.user);
           menu.display();

           switch (menu.getInput("Enter your choice: ")) {
               case 1 -> ca.addOrder();
               case 2 -> ca.updateOrder();
               case 3 -> ca.deleteOrder();
               case 4 -> {
                   return;
               }
           }
       }
    }
}
