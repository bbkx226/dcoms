package client.pages;

import client.OrderActions;
import client.components.Menu;
import models.Order;
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

public class OrderManagementInterface {
    private final OrderServiceRemote orderService;

    public OrderManagementInterface() throws MalformedURLException, NotBoundException, RemoteException {
        this.orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        OrderActions orderActions = new OrderActions();
        Scanner scanner = new Scanner(System.in);

        String[] headers = {"Order ID", "Customer ID", "Food ID", "Food Name","Price", "Quantity", "Total Price"};
        List<Order> orderList = orderService.getOrders();
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
        Table table = new Table("McGee's Order List", headers, rows);

        List<String> options = List.of("Create Order", "Update Order", "Delete Order", "Back");
        Menu menu = new Menu("Manage Orders", options, "Enter your choice:", 60);

        UIUtils.clrscr();
        table.display();
        menu.display();

        switch (menu.getInput()) {
            case 1:
                orderActions.createOrder();
                break;
            case 2:
//                        foodFunction.updateFood();
                break;
            case 3:
//                        foodFunction.deleteFood();
                break;
            case 4:
                return;
            default:
                System.out.println("\nInvalid input. Please try again.");
                scanner.nextLine();
        }
    }
}
