package client;

import models.Food;
import client.components.Table;
import models.Order;
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

public class OrderActions {

    public void createOrder() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        OrderServiceRemote orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");

        Scanner scanner = new Scanner(System.in);

        // Step 1: Select a user to place the order
        int selectedUserId = selectUserForOrder();

        if (selectedUserId == 0) { return; }

        // Step 2: Select the food to order
        int selectedFoodId = selectFoodForOrder();

        if (selectedFoodId == 0) { return; }

        // Step 3: Enter quantity of food to order and place the order
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

    public void updateOrder() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        OrderServiceRemote orderService = (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");
        OrderActions orderActions = new OrderActions();
        Scanner scanner = new Scanner(System.in);

        String[] headers = {"Order ID", "Customer ID", "Food ID", "Food Name","Price", "Quantity", "Total Price"};
        String prompt = "Enter the orderID to update: ";

        while (true) {
            try {
                List<Order> orderList = orderService.getOrders();
                List<String[]> rows = new ArrayList<>();
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
                }
                Table table = new Table("McGee's Order List", headers, rows);
                // ClearScreen.clrscr();

                // Check if there are no orders and return if true
                if (rows.isEmpty()) {
                    System.out.println("No data available. Press Enter to exit.");
                    scanner.nextLine();  // Wait for the user to press Enter
                    return;
                }

                // step 1 select a existing order
                table.display(); // display order data table
                int orderId = scanner.nextInt();
                Order SelectedOrder = orderService.getOrderByOrderId(orderId);

                if (orderId == -1) {
                    return;
                }

                // step 2 get a new order item
                int selectNewFoodId = selectFoodForOrder();

                if (selectNewFoodId == 0) {
                    return;
                }

                // step 3 get quantity
                int getQty = getOrderQuantity(selectNewFoodId);

                System.out.println(SelectedOrder);
                // step 4 set the food and update the quantity
                Food selectedFood = foodService.getFoodById(selectNewFoodId);

                SelectedOrder.setId(orderId);
                SelectedOrder.setFoodId(selectNewFoodId);;
                SelectedOrder.setFoodName(selectedFood.getName());
                SelectedOrder.setQuantity(getQty);
                SelectedOrder.setPrice(selectedFood.getPrice());
                System.out.println(SelectedOrder);
                System.out.println(SelectedOrder.getFoodId() + " " + SelectedOrder.getQuantity());

                // step 5 update the order
                boolean isUpdated = orderService.updateOrder(SelectedOrder);
                String checkUpdatedMessage = isUpdated ? "Order updated successfully." : "Failed to update order.";
                System.out.println(checkUpdatedMessage);


            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid input.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            } catch (RemoteException e) {
                System.out.println("Error occurred while updating order details: " + e.getMessage());
                System.out.println("Please try again later.");
            }
        }
    }

    private int selectUserForOrder() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();
        UserActions userActions = new UserActions();
        Scanner scanner = new Scanner(System.in);

        String[] headers = {"ID", "First Name", "Last Name", "IC/Passport"};
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

        Table table = new Table("A List of User", headers, rows);

        while (true) {
            try {
                table.display();
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

        String[] headers = {"ID", "Product", "Quantity", "Price"};
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

        Table table = new Table("A List of Food", headers, rows);

        while (true) {
            try {
                table.display();
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

    // get Quantity
    public int getOrderQuantity(int selectedFoodId) throws  MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        Scanner scanner = new Scanner(System.in);

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
                            scanner.nextLine();
                        } else if (qty > selectedFood.getQty()) {
                            System.out.println("Insufficient stock. Available quantity: " + selectedFood.getQty());
                            scanner.nextLine();

                        } else {
                            return qty;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                        scanner.next(); // Consume invalid input
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid options.");
                System.out.println("Press any key here to continue...");
                scanner.nextLine();
            }
        }

    }
}
