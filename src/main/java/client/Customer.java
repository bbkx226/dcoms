package client;

import models.Food;
import models.Menu;
import models.User;
import remote.FoodServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;


public class Customer {

   public int loginSuccessful() {
      System.out.println("Login Succcessful!");
      Scanner scanner = new Scanner (System.in);

      while (true) {
         List<String> options = List.of("Order","View Menu");
         Menu menu = new Menu("Welcome to McGee!!!", options, "Enter your choice:", "Exit", 60);
         try {
               int choice = menu.display();

               switch (choice) {
                  case 1:
                     orderFood(scanner);
                     break;
                  case 2:
                     viewMenu(scanner);
                     break;
                  case 0:
                     exitApp();
                     break;
                  default:
                     System.out.println("Invalid input. Please try again.");
                     scanner.nextLine();
               }
         } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 0 and 2.");
            System.out.println("\nPress any key to continue...");
            scanner.nextLine();
         } catch (RemoteException e) {
            System.out.println("Connection error: " + e.getMessage());
            System.out.println("\nPress any key to continue...");
            scanner.nextLine();
         }

      }

   }

   private static void orderFood(Scanner scanner) throws MalformedURLException, NotBoundException, RemoteException {
      System.out.println("Available food item: ");
      FoodServiceRemote foodService = (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
      List<Food> foodList = foodService.getAllFoods();
      for (Food food : foodList) {
         System.out.println(food.toString());
      }

      System.out.println("Enter the Food ID to order:");
      int foodId = scanner.nextInt();
      System.out.println("Enter the quantity:");
      int quantity = scanner.nextInt();


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

   private static void exitApp() {
      System.out.println("Thank you for using McGee! Goodbye!");
      System.exit(1);
   }

}
