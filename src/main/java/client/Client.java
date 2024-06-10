package client;

import models.Food;
import models.Order;
import models.User;
import remote.AuthServiceRemote;
import remote.FoodServiceRemote;
import remote.OrderServiceRemote;
import remote.UserServiceRemote;
import utils.ClearScreen;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to McGee! Please select an option to get started.");
            System.out.println("1. Login");
            System.out.println("2. View Menu");
            System.out.println("3. Register");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume/flush next line

            if (choice == 1) {
                System.out.println("Enter your username:");
                String username = scanner.nextLine();
                System.out.println("Enter your password:");
                String password = scanner.nextLine();

                User currentUser = authService.authenticate(username, password);
                if (currentUser == null) {
                    System.out.println("Invalid credentials. Please try again.");
                } else {
                    System.out.println(currentUser.getUserTypeString());
                }
            } else if (choice == 2) {
                List<Food> foodList = foodService.getAllFoods();
                for (Food food : foodList) {
                    System.out.println(food.toString());
                }
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            } else if (choice == 3) {
                // username and password cannot have space( ) or underscore(_)
                System.out.println("Registration menu");
                break;
            } else if (choice == 4) {
                System.exit(1);
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }
}
