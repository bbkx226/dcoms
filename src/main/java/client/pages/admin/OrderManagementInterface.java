package client.pages.admin;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.OrderActions;
import client.components.Menu;
import utils.UIUtils;

public class OrderManagementInterface {
    public static void start() throws MalformedURLException, NotBoundException, RemoteException {
        List<String> options = List.of("Create Order", "Update Order", "Delete Order", "Back");
        Menu menu = new Menu("Manage Orders", options);

        while (true) {
            UIUtils.clearScreen();
            OrderActions.displayAllOrders();
            menu.display();

            switch (menu.getInput("Enter your choice: ")) {
                case 1:
                    OrderActions.addOrder();
                    break;
                case 2:
                    OrderActions.updateOrder();
                    break;
                case 3:
                    OrderActions.deleteOrder();
                    break;
                case 4:
                    return;
            }
        }
    }
}
