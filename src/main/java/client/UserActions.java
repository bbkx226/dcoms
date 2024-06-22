package client;

import java.rmi.RemoteException;
import java.util.List;

import client.components.Menu;
import client.components.Table;
import models.User;
import remote.UserServiceRemote;
import utils.InputUtils;
import utils.UIUtils;

public class UserActions {
    public static void displayUserDetails(User user) {
        System.out.println("ID: " + user.getId());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("IC/Passport: " + user.getICNum());
    }

    public static void displayUsers() throws RemoteException {
        UserServiceRemote userService = RemoteServiceLocator.getUserService();
        if (userService == null) { return; }

        List<User> userList = userService.getAllUsers();

        String[] headers = {"ID", "First Name", "Last Name", "IC/Passport"};
        List<String[]> rows = userList.stream()
                .map(user -> new String[] {
                        String.valueOf(user.getId()),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getICNum()
                }).toList();

        Table table = new Table("User List", headers, rows);
        table.display();
    }

    public static User selectUserById() throws RemoteException {
        UserServiceRemote userService = RemoteServiceLocator.getUserService();
        if (userService == null) { return null; }

        int selectedId = InputUtils.intInput("Enter the ID of the user ('b' for back): ", "b");
        if (selectedId == Integer.MIN_VALUE) { return null; }
        User selectedUser = userService.getUserById(selectedId);
        if (selectedUser == null) {
            System.out.println("User not found in database.");
            InputUtils.waitForAnyKey();
            return null;
        }
        return selectedUser;
    }

    public static void updateUser() throws RemoteException {
        displayUsers();
        User selectedUser = selectUserById();
        if (selectedUser == null) { return; }

        // Prompt user to select detail to update
        while (true) {
            System.out.println();
            UIUtils.printLine(60);
            UIUtils.printHeader("Original User Details", 60);
            UIUtils.printLine(60);
            displayUserDetails(selectedUser);
            UIUtils.printLine(60);

            List<String> options = List.of("First Name", "Last Name", "IC/Passport", "Back");
            Menu menu = new Menu("", options);
            menu.display();

            switch (menu.getInput("Select a detail to update: ")) {
                case 1:
                    String newFirstName = InputUtils.stringInput("Enter new first name: ", "b");
                    if (newFirstName != null) { selectedUser.setFirstName(newFirstName); }
                    break;
                case 2:
                    String newLastName = InputUtils.stringInput("Enter new last name: ", "b");
                    if (newLastName != null) { selectedUser.setLastName(newLastName); }
                    break;
                case 3:
                    String newICNum = InputUtils.stringInput("Enter new IC/Passport: ", "b");
                    if (newICNum != null) { selectedUser.setICNum(newICNum); }
                    break;
                case 4:
                    return;
            }

            UserServiceRemote userService = RemoteServiceLocator.getUserService();
            if (userService == null) { return; }

            // Update the user details
            if (userService.updateUser(selectedUser)) {
                System.out.println("User details updated successfully in database.");
            } else {
                System.out.println("Failed to update user details in database.");
            }
        }
    }

    public static void deleteUser() throws RemoteException {
        while (true) {
            displayUsers();
            User selectedUser = selectUserById();
            if (selectedUser == null) { return; }

            // display user details and confirmation prompt
            System.out.println();
            UIUtils.printLine(60);
            System.out.println("Are you sure you want to delete the following user?");
            UIUtils.printLine(60);
            displayUserDetails(selectedUser);
            UIUtils.printLine(60);

            // prompt for confirmation
            char confirmation = InputUtils.charInput("type 'y' to confirm deletion, 'b' to cancel.", 'b');
            if (confirmation == '\0') {
                System.out.println("Deletion cancelled."); // exit the method and return to the previous menu
                return;
            }
            if (confirmation == 'y') {
                UserServiceRemote userService = RemoteServiceLocator.getUserService();
                if (userService == null) { return; }

                if (userService.removeUser(selectedUser)) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("Failed to delete user from database.");
                }
                break; // exit the loop after deletion
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'b'.");
            }
        }
    }
}
