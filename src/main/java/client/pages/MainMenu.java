package client.pages;

import client.components.Menu;
import client.components.Table;
import models.*;
import remote.FoodServiceRemote;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private final Menu menu;
    private final Scanner scanner;

    public MainMenu() {
        List<String> options = List.of("Login", "View Menu", "Register");
        this.menu = new Menu("Welcome to McGee!", options, "Enter your choice:", 60);
        this.scanner = new Scanner(System.in);
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        boolean exit = false;
        while (!exit) {
            UIUtils.clrscr();
            menu.display();

            switch (menu.getInput()) {
                case 1:
                    LoginInterface loginInterface = new LoginInterface();
                    loginInterface.start();
                    break;
                case 2:
                    viewFoodMenu();
                    break;
                case 3:
                    RegisterInterface registerInterface = new RegisterInterface();
                    registerInterface.start();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("\nInvalid input. Please try again.");
                    scanner.nextLine();
            }
        }
        exitApp();
    }

    private void viewFoodMenu() throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        List<Food> foodList = foodService.getAllFoods();

        String[] headers = {"ID", "Product", "Quantity", "Price"};
        List<String[]> rows = new ArrayList<>();
        List<Integer> optionsID = new ArrayList<>();

        for (Food food : foodList) {
            rows.add(new String[]{
                    String.valueOf(food.getId()),
                    food.getName(),
                    String.valueOf(food.getQty()),
                    String.format("$%.2f", food.getPrice())
            });
            optionsID.add(food.getId());
        }

        Table table = new Table("McGee Food Menu", headers, rows);
        table.display();

        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }

    private static void exitApp() {
        System.out.println("Thank you for using McGee! Goodbye!");
        System.exit(1);
    }
}
