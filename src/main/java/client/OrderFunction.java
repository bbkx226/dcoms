package client;

import models.Food;
import client.components.Table;
import models.User;
import remote.FoodServiceRemote;
import remote.OrderServiceRemote;
import remote.UserServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrderFunction  {

    public void createOrder() throws MalformedURLException, NotBoundException, RemoteException {
         UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
         FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
         OrderServiceRemote orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");

         Scanner scanner = new Scanner(System.in);

         int selectedUserId = selectUserForOrder(); // step 1 choose a user

        if (selectedUserId == 0) {
            return;
        }

         int selectedFoodId = selectFoodForOrder(); // step 2 choose a food

        if (selectedFoodId == 0) {
            return;
        }

        // step 3 enter quantity and create order
        Food selectedFood = foodService.getFoodById(selectedFoodId);

        while (true) {
            try {
                System.out.println("\n------------------------------------------------------------");
                System.out.println("Selected Food Details:");
                System.out.println("------------------------------------------------------------");
                System.out.println("ID: " + selectedFood.getId());
                System.out.println("Product Name: " + selectedFood.getName());
                System.out.println("Quantity: " + selectedFood.getQty());
                System.out.println("Price: " + selectedFood.getPrice());
                System.out.println("------------------------------------------------------------");

                int qty;
                while (true) {
                    System.out.print("Quantity: ");
                    if (scanner.hasNextInt()) {
                        qty = scanner.nextInt();
                        if (qty < 1) {
                            System.out.println("Quantity must be greater than 0.");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                        scanner.next(); // Consume invalid input
                    }
                }

                boolean orderCreated = orderService.addOrder(selectedUserId, selectedFoodId, qty);

                if (orderCreated) {
                    System.out.println("Order created successfully.");
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Failed to create order. Please check the quantity and try again.");
                }


            } catch (RemoteException e) {
                System.out.println("Error occurred while updating food details: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid options.");
                System.out.println("Press any key here to continue...");
                scanner.nextLine();
            }
        }

    }

    private int selectUserForOrder() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();
        UserActions userActions = new UserActions();
        Scanner scanner = new Scanner(System.in);

        String[] titles = {"ID", "First Name", "Last Name", "IC/Passport"};
        String prompt = "Enter the ID of the user before order ('b' for back): ";
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

        Table table = new Table("A List of User", new ArrayList<>(), optionsID, prompt, "");

        while (true) {
            try {
                table.display(rows, titles);
                if (scanner.hasNextInt()) { // Check the user input is int
                    int selectedUserId = scanner.nextInt();
                    boolean isUserExist = userService.checkUserId(selectedUserId);
                    if (isUserExist) {
                        return selectedUserId;
//                        userUpdateProcess(selectedUserId);
                    } else {
                        System.out.println("UserID does not exist.");
                        scanner.nextLine(); // Consume the newline
                    }
                } else { // user input is string
                    String userInput = scanner.next();
                    if (userInput.equalsIgnoreCase("b")) {
                        break; // Exit the loop if the user wants to go back
                    } else {
                        System.out.println("Invalid input. Please enter a valid userID or 'b' to go back.");
                        scanner.nextLine();
                    }
                }

            } catch (RemoteException e) {
                System.out.println("Error occurred while checking username existence: " + e.getMessage());
                System.out.println("Please try again later.");
                return 0;
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid userID.");
                scanner.nextLine();
            }
        }
        return 0;
    }

    private int selectFoodForOrder() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        List<Food> foodList = foodService.getAllFoods();
        Scanner scanner = new Scanner(System.in);

        String[] titles = {"ID", "Product", "Quantity", "Price"};
        String prompt = "Enter the ID of the food to order ('b' for back): ";
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

        Table table = new Table("A List of Food", new ArrayList<>(), optionsID, prompt, "");

        while (true) {
            try {
                table.display(rows, titles);
                if (scanner.hasNextInt()) { // Check the user input is int
                    int selectedFoodId = scanner.nextInt();
                    boolean isFoodExist = foodService.checkExistedFoodId(selectedFoodId);
                    if (isFoodExist) {
                        return selectedFoodId;
                    } else {
                        System.out.println("Food ID does not exist.");
                        scanner.nextLine(); // Consume the newline
                    }
                } else { // user input is string
                    String userInput = scanner.next();
                    if (userInput.equalsIgnoreCase("b")) {
                        break; // Exit the loop if the user wants to go back
                    } else {
                        System.out.println("Invalid input. Please enter a valid food ID or 'b' to go back.");
                        scanner.nextLine();
                    }
                }

            } catch (RemoteException e) {
                System.out.println("Error occurred while checking Food existence: " + e.getMessage());
                System.out.println("Please try again later.");
                return 0;
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid food ID.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            }
        }
        return 0;
    }
}
