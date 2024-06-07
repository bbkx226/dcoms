package client;

import models.User;
import models.UserType;
import remote.FoodOrderInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        FoodOrderInterface object = (FoodOrderInterface) Naming.lookup("rmi://localhost:7777/foodorder");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to McGee! Please select an option to get started.");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume/flush next line

            if (choice == 1) {
                System.out.println("Enter your username:");
                String username = scanner.nextLine();
                System.out.println("Enter your password:");
                String password = scanner.nextLine();

                if (!object.login(username, password)) {
                    System.out.println("Invalid credentials. Please try again.");
                } else {
                    System.out.println("login success");
                }
            } else if (choice == 2) {
                // username and password cannot have space( ) or underscore(_)
                System.out.println("Registration menu");
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }
}
