package client;

import client.components.Form;
import client.pages.RegisterInterface;
import client.components.Menu;
import client.components.Table;
import models.User;
import remote.UserServiceRemote;
import utils.InputUtils;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserActions {
    private int tableWidth = 60;

    public void viewUsers() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();

        String[] headers = {"ID", "First Name", "Last Name", "IC/Passport"};
        List<String[]> rows = new ArrayList<>();

        for (User user : userList) {
            rows.add(new String[] {
                    String.valueOf(user.getId()),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getICNum(),
            });
        }
        Table table = new Table("User List", headers, rows);
        tableWidth = table.getTotalWidth();
        table.display();
    }

    private int selectID() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        Form form = new Form();
        boolean isCancelled = !form.addIntField("ID", "Enter the ID of the user to update ('b' for back): ", input -> {
            try {
                return userService.checkUserId(input);
            } catch (RemoteException e) {
                System.out.println("Error occurred while checking user ID existence: " + e.getMessage());
                System.out.println("Please try again later.");
            }
            return false;
        }, "User ID does not exist.");
        if (isCancelled) { return Integer.MIN_VALUE; }
        return (int) form.getField("ID");
    }

    public void updateUserInterface() throws MalformedURLException, NotBoundException, RemoteException {
        viewUsers();
        int selectedID = selectID();
        if (selectedID == Integer.MIN_VALUE) { return; }
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");

        // Retrieve original user details
        User userToUpdate = userService.getUserById(selectedID);
        if (userToUpdate == null) {
            System.out.println("User not found in database.");
            return;
        }

        // Prompt user to select detail to update
        while (true) {
            System.out.println();
            UIUtils.line(60);
            UIUtils.printHeader("Original User Details", 60);
            UIUtils.line(60);
            System.out.println("ID: " + userToUpdate.getId());
            System.out.println("First Name: " + userToUpdate.getFirstName());
            System.out.println("Last Name: " + userToUpdate.getLastName());
            System.out.println("IC/Passport: " + userToUpdate.getICNum());
            UIUtils.line(60);

            List<String> options = List.of("First Name", "Last Name", "IC/Passport", "Back");
            Menu menu = new Menu("", options, "Select a detail to update: ", tableWidth);
            menu.display();

            switch (menu.getInput()) {
                case 1:
                    String newFirstName = InputUtils.stringInput("Enter new first name: ", "b");
                    if (newFirstName != null) { userToUpdate.setFirstName(newFirstName); }
                    break;
                case 2:
                    String newLastName = InputUtils.stringInput("Enter new last name: ", "b");
                    if (newLastName != null) { userToUpdate.setLastName(newLastName); }
                    break;
                case 3:
                    System.out.println("Enter New IC/Passport: ");
                    String newICNum = InputUtils.stringInput("Enter new IC/Passport: ", "b");
                    if (newICNum != null) { userToUpdate.setICNum(newICNum); }
                    break;
                case 4:
                    // Exit the method if user selects "Back"
                    return;
            }

            // Update the user details
            if (userService.updateUser(userToUpdate)) {
                System.out.println("User details updated successfully in database.");
            } else {
                System.out.println("Failed to update user details in database.");
            }
        }
    }

    public void deleteUserInterface() throws MalformedURLException, NotBoundException, RemoteException {
        viewUsers();
        int selectedID = selectID();
        if (selectedID == Integer.MIN_VALUE) { return; }
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");

        while (true) {
            // retrieve the user details
            User userToDelete = userService.getUserById(selectedID);

            // handle null usertodelete object
            if (userToDelete == null) {
                System.out.println("User not found in database.");
                return;
            }

            // display user details and confirmation prompt
            System.out.println();
            UIUtils.line(60);
            System.out.println("Are you sure you want to delete the following user?");
            UIUtils.line(60);
            System.out.println("ID: " + userToDelete.getId());
            System.out.println("first name: " + userToDelete.getFirstName());
            System.out.println("last name: " + userToDelete.getLastName());
            System.out.println("ic/passport: " + userToDelete.getICNum());
            UIUtils.line(60);
            System.out.println("type 'y' to confirm deletion, 'n' to cancel, or 'b' to go back.");

            // prompt for confirmation
            char confirmation = InputUtils.charInput("type 'y' to confirm deletion, 'n' to cancel, or 'b' to go back.", 'b');
            if (confirmation == 'y') {
                if (userService.removeUser(userToDelete)) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("Failed to delete user from database.");
                }
                break; // exit the loop after deletion
            } else if (confirmation == 'n') {
                System.out.println("Deletion canceled.");
                break; // exit the loop if deletion is canceled
            } else if (confirmation == 'b') {
                System.out.println("Returning to previous menu.");
                return; // exit the method and return to the previous menu
            } else {
                System.out.println("Invalid input. Please enter 'y', 'n', or 'b'.");
            }
        }
    }
}
