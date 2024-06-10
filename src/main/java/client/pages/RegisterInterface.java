package client.pages;


import remote.UserServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RegisterInterface {
    private final UserServiceRemote userService;
    private final Scanner scanner;

    public RegisterInterface() throws MalformedURLException, NotBoundException, RemoteException {
        userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        scanner = new Scanner(System.in);
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            System.out.println("--------------------------------------------------");
            System.out.println("                   McGee Register                 ");
            System.out.println("--------------------------------------------------");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();
            System.out.print("IC/Passport Number: ");
            String ICNum = scanner.nextLine().trim();
            String username;
            boolean isExisted;
            do {
                System.out.print("Username: ");
                username = scanner.nextLine().trim();
                try {
                    isExisted = userService.checkUserName(username);
                    if (isExisted) {
                        System.out.println("Username already exists. Please try again.");
                    }
                } catch (RemoteException e) {
                    System.out.println("Error occurred while checking username existence: " + e.getMessage());
                    System.out.println("Please try again later.");
                    return; // Exit the method on RemoteException
                }
            } while (isExisted);

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            boolean isCreated = userService.addUser(firstName, lastName, ICNum, username, password);

            System.out.println("User created successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
