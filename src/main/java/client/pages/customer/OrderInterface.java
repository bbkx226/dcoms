package client.pages.customer;

import client.CustomerActions;
import client.OrderActions;
import client.components.Menu;
import models.User;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class OrderInterface {
    private final User user;

    public OrderInterface(User user) {
        this.user = user;
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
       List<String> options = List.of("Place Order", "Update Order", "Delete Order", "Back");
       Menu menu = new Menu("Manage your orders", options);

       while (true) {
           UIUtils.clrscr();
           OrderActions.displayUserOrders(this.user);
           CustomerActions ca = new CustomerActions(this.user);
           menu.display();

           switch (menu.getInput("Enter your choice: ")) {
               case 1:
                   ca.addOrder();
                   break;
               case 2:
                   ca.updateOrder();
                   break;
               case 3:
                   ca.deleteOrder();
                   break;
               case 4:
                   return;
           }
       }
    }
}
