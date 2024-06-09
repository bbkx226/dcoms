package client;

import models.Menu;
import models.Table;
import remote.UserServiceRemote;
import utils.ClearScreen;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import models.User;

public class AdminInterface {
    private User user;

    public void adminMenu(User user) throws MalformedURLException, NotBoundException, RemoteException {
        this.user = user;
        Scanner scanner = new Scanner(System.in); // Fix typo: System.io should be System.in

        while (true) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            List<String> options = List.of("View User Details", "View Menu", "Check Order", "Order", "Back");
            Menu menu = new Menu(" Welcome to McGee, " + fullName + "!!!", options, "Enter your choice:", "", 60);
            try {
//                ClearScreen.clrscr();
                int choice = menu.display();

                switch (choice) {
                    case 1:
                        viewUserDetails();
                        break;
                    case 2:
//                        viewMenu(scanner);
                        break;
                    case 3:
                        // Implement checkOrder() method
                        break;
                    case 4:
                        // Implement order() method
                        break;
                    case 5: // Back option
                        return; // Return to the previous menu or perform any desired action
                    default:
                        System.out.println("\nInvalid input. Please try again.");
                        System.out.println("\nPress any key to continue...");
                        scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number between 1 and 5.");
                System.out.println("\nPress any key to continue...");
                scanner.nextLine();
            }
        }
    }

    private void viewUserDetails() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();
        UserFunction userFunction = new UserFunction();
        Scanner scanner = new Scanner(System.in);

        List<String> options = List.of("Create User", "Update User", "Delete User", "Back");
        String[] titles = {"ID", "First Name", "Last Name", "IC/Passport"};
        String prompt = "Enter an action: ";
        List<String[]> rows = new ArrayList<>();

        for (User user : userList) {
            rows.add(new String[] {
                String.valueOf(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getICNum(),
            });
        }

        Table table = new Table("A List of User", options, prompt, "");

        try {
            // ClearScreen.clrscr();
            int choice = table.displayTable(rows, titles);

            switch (choice) {
                case 1:
                    userFunction.createUser();
                    break;
                case 2:
                    userFunction.updateUserInterface();
                    break;
                case 3:
                    userFunction.deleteUserInterface();
//                    register.register();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid input. Please try again.");
                    scanner.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter a number between 1 and 4.");
            System.out.println("\nPress any key to continue...");
            scanner.nextLine();
        }


    }
}
