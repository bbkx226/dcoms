package client;

import models.Food;
import models.Menu;
import models.Table;
import models.User;
import remote.FoodServiceRemote;
import remote.UserServiceRemote;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FoodFunction {

    public void createFood() throws MalformedURLException, NotBoundException, RemoteException{
        try {
            Scanner scanner = new Scanner(System.in);
            FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");

            System.out.println("--------------------------------------------------");
            System.out.println("                   McGee Food                     ");
            System.out.println("--------------------------------------------------");
            while (true) {
                System.out.print("Product Name: ");
                String name = scanner.nextLine().trim();

                // Check if the food already exists
                if (foodService.checkIsFoodExisted(name)) {
                    System.out.println("Product already exists! Please enter a different product name.");
                    continue; // Skip to the next iteration to prompt for product name again
                }

                int qty;
                while (true) {
                    System.out.print("Quantity: ");
                    if (scanner.hasNextInt()) {
                        qty = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid quantity (integer).");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }

                double price;
                while (true) {
                    System.out.print("Price: ");
                    if (scanner.hasNextDouble()) {
                        price = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid price (decimal number).");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }

                boolean isCreated = foodService.addFood(name, qty, price);
                if (isCreated) {
                    System.out.println("Product created successfully!");
                    break; // Exit the loop if the product is created successfully
                } else {
                    System.out.println("Product already exists! Please enter the details again.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateFood() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        List<Food> foodList = foodService.getAllFoods();
        Scanner scanner = new Scanner(System.in);

        String[] titles = {"ID", "Product", "Quantity", "Price"};
        String prompt = "Enter the ID of the food to update ('b' for back): ";
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
                table.displayTable(rows, titles);
                if (scanner.hasNextInt()) { // Check the user input is int
                    int selectedFoodId = scanner.nextInt();
                    boolean isFoodExist = foodService.checkExistedFoodId(selectedFoodId);
                    if (isFoodExist) {
                        foodUpdateProcess(selectedFoodId);
                        break;
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
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid food ID.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            }
        }
    }

    public void deleteFood() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        List<Food> foodList = foodService.getAllFoods();
        Scanner scanner = new Scanner(System.in);

        String[] titles = {"ID", "Product", "Quantity", "Price"};
        String prompt = "Enter the ID of the food to update ('b' for back): ";
        List<String[]> rows = new ArrayList<>();
        List<Integer> optionsID = new ArrayList<>();
        Integer[] menuOptions = {1,2,3,4};

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
                table.displayTable(rows, titles);
                if (scanner.hasNextInt()) { // Check the user input is int
                    int selectedFoodId = scanner.nextInt();
                    boolean isFoodExist = foodService.checkExistedFoodId(selectedFoodId);
                    if (isFoodExist) {
                        foodDeleteProcess(selectedFoodId);
                        break;
                    } else {
                        System.out.println("FoodID does not exist.");
                        scanner.nextLine(); // Consume the newline
                    }
                } else { // user input is string
                    String userInput = scanner.next();
                    if (userInput.equalsIgnoreCase("b")) {
                        break; // Exit the loop if the user wants to go back
                    } else {
                        System.out.println("Invalid input. Please enter a valid foodID or 'b' to go back.");
                        scanner.nextLine();
                    }
                }

            } catch (RemoteException e) {
                System.out.println("Error occurred while checking username existence: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid foodID.");
//                System.out.println("Press any key here to continue...");
            }
        }


    }

    private void foodDeleteProcess(int selectedFoodId) throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        Food foodToDelete = foodService.getFoodById(selectedFoodId);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n------------------------------------------------------------");
                System.out.println("Are you sure you want to delete the following food?");
                System.out.println("------------------------------------------------------------");
                System.out.println("ID: " + foodToDelete.getId());
                System.out.println("Product Name: " + foodToDelete.getName());
                System.out.println("Quantity: " + foodToDelete.getQty());
                System.out.println("Pice: " + foodToDelete.getPrice());
                System.out.println("------------------------------------------------------------");
                System.out.println("Type 'y' to confirm deletion, 'n' to cancel, or 'b' to go back.");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("y")) {
                    boolean deleted = foodService.removeFood(selectedFoodId);
                    if (deleted) {
                        System.out.println("Food deleted successfully.");
                    } else {
                        System.out.println("Failed to delete food.");
                    }
                    break; // Exit the loop after deletion
                } else if (confirmation.equalsIgnoreCase("n")) {
                    System.out.println("Deletion canceled.");
                    break; // Exit the loop if deletion is canceled
                } else if (confirmation.equalsIgnoreCase("b")) {
                    System.out.println("Returning to previous menu.");
                    return; // Exit the method and return to the previous menu
                } else {
                    System.out.println("Invalid input. Please enter 'y', 'n', or 'b'.");
                }
            } catch (RemoteException e) {
                System.out.println("Error occurred while deleting user details: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid options.");
                System.out.println("Press any key here to continue...");
                scanner.nextLine();
            }
        }

    }

    private void foodUpdateProcess(int selectedFoodId) throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        Scanner scanner = new Scanner(System.in);

        // Retrieve original user details
        Food foodToUpdate = foodService.getFoodById(selectedFoodId);
        if (foodToUpdate == null) {
            System.out.println("Food not found.");
            return;
        }

        // Display original user details

        // Prompt user to select detail to update
        while (true) {
            try {
                System.out.println("\n------------------------------------------------------------");
                System.out.println("Original Food Details:");
                System.out.println("------------------------------------------------------------");
                System.out.println("ID: " + foodToUpdate.getId());
                System.out.println("Product Name: " + foodToUpdate.getName());
                System.out.println("Quantity: " + foodToUpdate.getQty());
                System.out.println("Price: " + foodToUpdate.getPrice());
                System.out.println("------------------------------------------------------------");

                List<String> options = List.of("Product Name", "Quantity", "Price", "Back");
                Menu menu = new Menu("", options, "Select a product detail to update: ", "", 60);
                int choice = menu.display();

                switch (choice) {
                    case 1:
                        while (true) {
                            System.out.print("Enter New Product Name: ");
                            String productName = scanner.nextLine().trim();
                            if (foodService.checkIsFoodExisted(productName)) {
                                System.out.println("Product already exists! Please enter a different product name.");
                            } else {
                                foodToUpdate.setName(productName);
                                break; // Exit the loop once a unique name is entered
                            }
                        }
                        break;
                    case 2:
                        System.out.print("Enter New Quantity: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid quantity.");
                            scanner.next(); // Clear the invalid input
                        }
                        int qty = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        foodToUpdate.setQty(qty);
                        break;
                    case 3:
                        System.out.print("Enter New Price: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("Invalid input. Please enter a valid price.");
                            scanner.next(); // Clear the invalid input
                        }
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        foodToUpdate.setPrice(price);
                        break;
                    case 4:
                        // Exit the method if user selects "Back"
                        return;
                    default:
                        System.out.println("\nInvalid input. Please try again.");
                        continue;
                }

                // Update the user details
                boolean success = foodService.updateFood(foodToUpdate);
                if (success) {
                    System.out.println("Food details updated successfully.");
                } else {
                    System.out.println("Failed to update food details.");
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
}
