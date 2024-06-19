package client;

import client.components.Form;
import models.Food;
import client.components.Menu;
import client.components.Table;
import remote.FoodServiceRemote;
import utils.InputUtils;
import utils.UIUtils;

import java.rmi.RemoteException;
import java.util.List;

public class FoodActions {
    public static void displayFoodDetails(Food food) {
        System.out.println("ID: " + food.getId());
        System.out.println("Product Name: " + food.getName());
        System.out.println("Quantity: " + food.getQty());
        System.out.println("Price: " + food.getPrice());
    }

    public static void displayFoods() throws RemoteException {
        FoodServiceRemote foodService = RemoteServiceLocator.getFoodService();
        if (foodService == null) { return; }

        List<Food> foodList = foodService.getAllFoods();

        String[] headers = {"ID", "Product", "Quantity", "Price"};
        List<String[]> rows = foodList.stream()
                .map(food -> new String[] {
                    String.valueOf(food.getId()),
                    food.getName(),
                    String.valueOf(food.getQty()),
                    String.format("$%.2f", food.getPrice())
                }).toList();

        Table table = new Table("McGee Food Menu", headers, rows);
        table.display();
    }

    public static Food selectFoodById() throws RemoteException {
        FoodServiceRemote foodService = RemoteServiceLocator.getFoodService();
        if (foodService == null) { return null; }

        int selectedId = InputUtils.intInput("Enter the ID of the product ('b' for back): ", "b");
        if (selectedId == Integer.MIN_VALUE) { return null; }
        Food selectedFood = foodService.getFoodById(selectedId);
        if (selectedFood == null) {
            System.out.println("Product not found in database.");
            InputUtils.waitForAnyKey();
            return null;
        }
        return selectedFood;
    }

    public static void addFood() throws RemoteException {
        UIUtils.line(50);
        UIUtils.printHeader("McGee Food", 50);
        UIUtils.line(50);

        FoodServiceRemote foodService = RemoteServiceLocator.getFoodService();
        if (foodService == null) { return; }
        List<Food> foodList = foodService.getAllFoods();
        List<String> foodNames = foodList.stream()
                .map(Food::getName)
                .toList();

        Form form = new Form();
        String name;
        while (true) {
            if (!form.addStringField("name", "Product Name: ")) { return; }
            name = (String) form.getField("name");
            if (foodNames.contains(name)) {
                System.out.println("Product already exists! Please enter a different product name.");
            } else break;
        }
        if (!form.addIntField("quantity", "Quantity: ", input -> input > 0, "Invalid input. Please enter a valid quantity.")) { return; }
        if (!form.addDoubleField("price", "Price: ", input -> input > 0,"Invalid input. Please enter a valid price.")) { return; }

        int qty = (int) form.getField("quantity");
        double price = (double) form.getField("price");

        boolean isFoodAdded = foodService.addFood(name, qty, price);
        if (isFoodAdded) {
            System.out.println("Product added successfully!");
        } else {
            System.out.println("Product already exists! Please enter the details again.");
        }
    }

    public static void updateFood() throws RemoteException {
        displayFoods();
        Food selectedFood = selectFoodById();
        if (selectedFood == null) { return; }

        // Prompt user to select detail to update
        while (true) {
            System.out.println();
            UIUtils.line(60);
            UIUtils.printHeader("Original Product Details", 60);
            UIUtils.line(60);
            displayFoodDetails(selectedFood);
            UIUtils.line(60);

            List<String> options = List.of("Product Name", "Quantity", "Price", "Back");
            Menu menu = new Menu("", options);
            menu.display();

            FoodServiceRemote foodService = RemoteServiceLocator.getFoodService();
            if (foodService == null) { return; }

            switch (menu.getInput("Select a detail to update: ")) {
                case 1:
                    String newName = InputUtils.stringInput("Enter product's new name: ", "b");
                    if (newName == null) { break; }

                    List<Food> foodList = foodService.getAllFoods();
                    boolean isNameExists = foodList.stream()
                            .map(Food::getName)
                            .anyMatch(name -> !name.equals(selectedFood.getName()) && name.equals(newName));
                    if (isNameExists) {
                        System.out.println("Product already exists! Please enter a different product name.");
                        continue;
                    }
                    selectedFood.setName(newName);
                    break;
                case 2:
                    int newQty = InputUtils.intInput("Enter product quantity: ", "b");
                    if (newQty == Integer.MIN_VALUE) { break; }
                    if (newQty < 0) {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                        continue;
                    }
                    selectedFood.setQty(newQty);
                    break;
                case 3:
                    double newPrice = InputUtils.doubleInput("Enter product's new price: ", "b");
                    if (Double.isNaN(newPrice)) { break; }
                    if (newPrice < 0) {
                        System.out.println("Invalid input. Please enter a valid price.");
                        continue;
                    }
                    selectedFood.setPrice(newPrice);
                    break;
                case 4:
                    return;
            }

            // Update the food details
            if (foodService.updateFood(selectedFood)) {
                System.out.println("Food details updated successfully.");
            } else {
                System.out.println("Failed to update food details.");
            }
        }
    }

    public static void deleteFood() throws RemoteException {
        while (true) {
            displayFoods();
            Food selectedFood = selectFoodById();
            if (selectedFood == null) { return; }

            // display food details and confirmation prompt
            System.out.println();
            UIUtils.line(60);
            System.out.println("Are you sure you want to delete the following product?");
            UIUtils.line(60);
            displayFoodDetails(selectedFood);
            UIUtils.line(60);

            // prompt for confirmation
            char confirmation = InputUtils.charInput("Type 'y' to confirm deletion, 'b' to cancel: ", 'b');
            if (confirmation == '\0') {
                System.out.println("Deletion cancelled.");
                return;
            }
            if (confirmation == 'y') {
                FoodServiceRemote foodService = RemoteServiceLocator.getFoodService();
                if (foodService == null) { return; }

                if (foodService.removeFood(selectedFood.getId())) {
                    System.out.println("Product deleted successfully.");
                } else {
                    System.out.println("Failed to delete product from database.");
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'b'.");
            }
        }
    }
}