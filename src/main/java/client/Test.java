package client;

import models.Food;
import models.Order;
import models.User;

import remote.AuthServiceRemote;
import remote.FoodServiceRemote;
import remote.OrderServiceRemote;
import remote.UserServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.List;

public class Test {
    public static void test() throws MalformedURLException, NotBoundException, RemoteException {
        AuthServiceRemote authService = (AuthServiceRemote) Naming.lookup("rmi://localhost:7777/authService");
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        OrderServiceRemote orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");

        // remote testing section, remove later or implement in testing part
        System.out.println("-------------------------------------------- Beginning of simple test section --------------------------------------------");
        System.out.println("Auth testing");
        System.out.println(authService.authenticate("tkz", "123").toString());
        System.out.println(authService.authenticate("axt", "123").toString());
        System.out.println(authService.authenticate("bran", "123").toString());
        System.out.println(authService.authenticate("kaizhe", "123").toString());
        try {
            System.out.println(authService.authenticate("wrong username", "wrong password").toString());
        } catch (Exception e) {
            System.err.println("incorrect username / password");
        }

        List<User> users = userService.getAllUsers();
        System.out.println("\nUserService testing");
        for (User user : users) {
            System.out.println(user.toString());
        }

        List<Food> foods = foodService.getAllFoods();
        System.out.println("\nFoodService testing");
        for (Food food : foods) {
            System.out.println(food.toString());
        }

        List<Order> orders = orderService.getOrders(); // should be empty
        System.out.println("\nOrderService testing");
        System.out.println("Initial orders is empty: " + orders.isEmpty());
        orderService.addOrder(1, 10);
        List<Order> orders1 = orderService.getOrders(); // should have one order
        System.out.println("Orders list after adding one order: ");
        for (Order order : orders1) {
            System.out.println(order.toString());
        }
        boolean result = orderService.addOrder(1, 45);
        System.out.println("Trying to add an order with quantity higher than current food quantity (same food item): " + result);
        boolean result1 = orderService.addOrder(2, 101);
        System.out.println("Trying to add an order with quantity higher than current food quantity (new food item): " + result1);
        List<Order> orders2 = orderService.getOrders(); // should still have one order
        for (Order order : orders2) {
            System.out.println(order.toString());
        }
        //TODO: test checkout function
        System.out.println("\n-------------------------------------------- End of testing section --------------------------------------------\n\n");
        // end of remote testing section
    }
}
