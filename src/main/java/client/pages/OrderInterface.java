package client.pages;

import client.CustomerActions;
import client.components.Menu;
import models.Order;
import models.User;
import client.components.Table;
import remote.OrderServiceRemote;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderInterface {
    private final OrderServiceRemote orderService;
    private final User user;

    public OrderInterface(User user) throws MalformedURLException, NotBoundException, RemoteException {
        this.orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");
        this.user = user;
    }

    public void orderMenu() throws MalformedURLException, NotBoundException, RemoteException {
       CustomerActions customerActions = new CustomerActions(user);
       Scanner scanner = new Scanner(System.in);

       String[] headers = {"Order ID", "Customer ID", "Food ID", "Food Name", "Price", "Quantity", "Total Price"};
       List<Order> orderList = orderService.getOrders();

       //Filter orders by Customer ID
        orderList = orderList.stream()
                .filter(order -> order.getUserId() == this.user.getId())
                .collect(Collectors.toList());



       List<String[]> rows = new ArrayList<>();
       for (Order order : orderList) {
           rows.add(new String[]{
                   String.valueOf(order.getId()),
                   String.valueOf(order.getUserId()),
                   String.valueOf(order.getFoodId()),
                   order.getFoodName(),
                   String.valueOf(order.getPrice()),
                   String.valueOf(order.getQuantity()),
                   String.valueOf(order.getTotalPrice()),
           });
       }
       Table table = new Table ("McGee's Order List", headers, rows);

       List<String> options = List.of("Order Food", "Update Order", "Delete Order", "Back");
       Menu menu = new Menu("Order Food", options, "Enter your choice:", 60);

       UIUtils.clrscr();
       table.display();
       menu.display();

       switch (menu.getInput()) {
           case 1:
               customerActions.orderFood(user);
               break;
           case 2:
               customerActions.updateOrder(user);
               break;
           case 3:
               customerActions.deleteOrder(user);
               break;
           case 4:
               return;
           default:
               System.out.println("\nInvalid input. Please try again.");
               scanner.nextLine();
       }
    }
}
