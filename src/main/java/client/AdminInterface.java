package client;

import models.*;
import remote.FoodServiceRemote;
import remote.OrderServiceRemote;
import remote.UserServiceRemote;
import utils.ClearScreen;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminInterface {
    private User user;

    public void adminMenu(User user) throws MalformedURLException, NotBoundException, RemoteException {
        this.user = user;
        Scanner scanner = new Scanner(System.in); // Fix typo: System.io should be System.in

        while (true) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            List<String> options = List.of("View User Details", "View Menu", "Check Order", "Order", "Back");
            Menu menu = new Menu(" Welcome to McGee, " + fullName + "!!!", options, "Enter your choice:", "", 60);
            try {
//                ClearScreen.clrscr();
                int choice = menu.display();

                switch (choice) {
                    case 1:
                        viewUserDetails();
                        break;
                    case 2:
                        viewFoodMenu();
                        break;
                    case 3:
                        viewOrderMenu();
                        break;
                    case 4:
                        // Implement order() method
                        break;
                    case 5: // Back option
                        return; // Return to the previous menu or perform any desired action
                    default:
                        System.out.println("\nInvalid input. Please try again.");
                        System.out.println("\nPress any key to continue...");
                        scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number between 1 and 5.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            }
        }
    }

    private void viewUserDetails() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();
        UserFunction userFunction = new UserFunction();
        Scanner scanner = new Scanner(System.in);

        List<String> options = List.of("Create User", "Update User", "Delete User", "Back");
        String[] titles = {"ID", "First Name", "Last Name", "IC/Passport"};
        String prompt = "Enter an action: ";
        List<String[]> rows = new ArrayList<>();
        List<Integer> optionsID = new ArrayList<>();

        for (User user : userList) {
            rows.add(new String[] {
                String.valueOf(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getICNum(),
            });
            optionsID.add(user.getId());
        }

        Table table = new Table("A List of User", options, optionsID, prompt, "");

        try {
            // ClearScreen.clrscr();
            int choice = table.displayTable(rows, titles);

            switch (choice) {
                case 1:
                    userFunction.createUser();
                    break;
                case 2:
                    userFunction.updateUserInterface();
                    break;
                case 3:
                    userFunction.deleteUserInterface();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid input. Please try again.");
                    scanner.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter a number between 1 and 4.");
            System.out.println("\nPress any key to continue...");
            scanner.nextLine();
        }
    }

    private void viewFoodMenu() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        List<Food> foodList = foodService.getAllFoods();
        FoodFunction foodFunction = new FoodFunction();
        Scanner scanner = new Scanner(System.in);

        List<String> options = List.of("Create Product", "Update Product", "Delete Product", "Back");
        String[] titles = {"ID", "Product", "Quantity", "Price"};
        String prompt = "Enter an action: ";
        List<String[]> rows = new ArrayList<>();
        List<Integer> optionsID = new ArrayList<>();

        for (Food food : foodList) {
            rows.add(new String[] {
                String.valueOf(food.getId()),
                food.getName(),
                String.valueOf(food.getQty()),
                String.valueOf(food.getPrice()),
            });
            optionsID.add(food.getId());
        }

        Table table = new Table("McGee's Food List", options, optionsID, prompt, "");
        while (true) {
            try {
                // ClearScreen.clrscr();
                int choice = table.displayTable(rows, titles);

                switch (choice) {
                    case 1:
                        foodFunction.createFood();
                        break;
                    case 2:
                        foodFunction.updateFood();
                        break;
                    case 3:
                        foodFunction.deleteFood();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("\nInvalid input. Please try again.");
                        scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number between 1 and 4.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            } catch (RemoteException e) {
                System.out.println("Error occurred while updating user details: " + e.getMessage());
                System.out.println("Please try again later.");
            }
        }
    }

    private void viewOrderMenu() throws MalformedURLException, NotBoundException, RemoteException {
        OrderServiceRemote orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");
        OrderFunction orderFunction = new OrderFunction();
        Scanner scanner = new Scanner(System.in);

        List<String> options = List.of("Create Order", "Update Order", "Delete Order", "Back");
        String[] titles = {"Order ID", "Customer ID", "Food ID", "Food Name","Price", "Quantity", "Total Price"};
        String prompt = "Enter an action: ";


        while (true) {
            try {
                List<Order> orderList = orderService.getOrders();
                List<String[]> rows = new ArrayList<>();
                List<Integer> optionsID = new ArrayList<>();
                for (Order order : orderList) {
                    rows.add(new String[] {
                    String.valueOf(order.getId()),
                    String.valueOf(order.getUserId()),
                    String.valueOf(order.getFoodId()),
                    order.getFoodName(),
                    String.valueOf(order.getPrice()),
                    String.valueOf(order.getQuantity()),
                    String.valueOf(order.getTotalPrice()),
                });
                optionsID.add(order.getId());
                }
                Table table = new Table("McGee's Order List", options, optionsID, prompt, "");
                // ClearScreen.clrscr();
                int choice = table.displayTable(rows, titles);

                switch (choice) {
                    case 1:
                        orderFunction.createOrder();
                        break;
                    case 2:
                        orderFunction.updateOrder();
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
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number between 1 and 4.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            } catch (RemoteException e) {
                System.out.println("Error occurred while updating user details: " + e.getMessage());
                System.out.println("Please try again later.");
            }
        }
    }
}
