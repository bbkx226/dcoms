package client.pages;

import client.FoodActions;
import models.Food;
import client.components.Menu;
import client.components.Table;
import remote.FoodServiceRemote;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodManagementInterface {
    private final FoodServiceRemote foodService;

    public FoodManagementInterface() throws MalformedURLException, NotBoundException, RemoteException {
        this.foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        List<Food> foodList = foodService.getAllFoods();
        FoodActions foodActions = new FoodActions();
        Scanner scanner = new Scanner(System.in);

        String[] headers = {"ID", "Product", "Quantity", "Price"};
        List<String[]> rows = new ArrayList<>();
        for (Food food : foodList) {
            rows.add(new String[] {
                    String.valueOf(food.getId()),
                    food.getName(),
                    String.valueOf(food.getQty()),
                    String.valueOf(food.getPrice()),
            });
        }
        Table table = new Table("McGee's Food List", headers, rows);

        List<String> options = List.of("Create Product", "Update Product", "Delete Product", "Back");
        Menu menu = new Menu("Manage Food", options, "Enter your choice: ");

        while (true) {
            UIUtils.clrscr();
            table.display();
            menu.display();

            switch (menu.getInput()) {
                case 1:
                    foodActions.createFood();
                    break;
                case 2:
                    foodActions.updateFood();
                    break;
                case 3:
                    foodActions.deleteFood();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid input. Please try again.");
                    scanner.nextLine();
            }
        }
    }
}
