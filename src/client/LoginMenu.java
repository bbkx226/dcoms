package client;

import models.Food;
import models.Menu;
import models.User;
import remote.AuthServiceRemote;
import remote.FoodServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LoginMenu {

    public int loginMenu() throws MalformedURLException, NotBoundException, RemoteException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            List<String> options = List.of("Login", "View Menu", "Register");
            Menu menu = new Menu("Welcome to McGee!!!", options, "Enter your choice:", "Exit", 60);
            try {
                int choice = menu.display();

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        viewMenu(scanner);
                        break;
                    case 3:
                        register();
                        break;
                    case 0:
                        exitApp();
                        break;
                    default:
                        System.out.println("Invalid input. Please try again.");
                        scanner.nextLine();
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();

            }
        }
    }

    private static void login() throws MalformedURLException, NotBoundException, RemoteException {
        Scanner scanner = new Scanner(System.in);
        AuthServiceRemote authService = (AuthServiceRemote) Naming.lookup("rmi://localhost:7777/authService");
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        User currentUser = authService.authenticate(username, password);
        if (currentUser == null) {
            System.out.println("Invalid credentials. Please try again.");
        } else { // Success login
            System.out.println(currentUser.getUserTypeString());
        }


    }

    private static void viewMenu(Scanner scanner) throws MalformedURLException, NotBoundException, RemoteException {
        FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        List<Food> foodList = foodService.getAllFoods();
        for (Food food : foodList) {
            System.out.println(food.toString());
        }
        System.out.println("\nPress any key to continue...");
        scanner.nextLine();
    }

    private static void register() {
        System.out.println("Registration menu");
        // Add registration logic here
    }

    private static void exitApp() {
        System.out.println("Thank you for using McGee! Goodbye!");
        System.exit(1);
    }



}
