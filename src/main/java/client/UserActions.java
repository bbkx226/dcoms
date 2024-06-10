package client;

import client.pages.RegisterInterface;
import client.components.Menu;
import client.components.Table;
import models.User;
import remote.UserServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserActions {
    private UserServiceRemote userService;

    public void createUser() throws MalformedURLException, NotBoundException, RemoteException {
        RegisterInterface register = new RegisterInterface();
        register.start();
        userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
    }

    public void updateUserInterface() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();
        UserActions userActions = new UserActions();
        Scanner scanner = new Scanner(System.in);

        String[] headers = {"ID", "First Name", "Last Name", "IC/Passport"};
        String prompt = "Enter the ID of the user to update ('b' for back): ";
        List<String[]> rows = new ArrayList<>();
        List<Integer> optionsID = new ArrayList<>();

        for (User user : userList) {
            rows.add(new String[] {
                    String.valueOf(user.getId()),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getICNum(),
            });
            optionsID.add(user.getId());
        }

        Table table = new Table("A List of User", headers, rows);

        while (true) {
            try {
                table.display();
                System.out.print("Enter the ID of the user to update ('b' for back): ");
                if (scanner.hasNextInt()) { // Check the user input is int
                    int selectedUserId = scanner.nextInt();
                    boolean isUserExist = userService.checkUserId(selectedUserId);
                    if (isUserExist) {
                        userUpdateProcess(selectedUserId);
                        break;
                    } else {
                        System.out.println("UserID does not exist.");
                        scanner.nextLine(); // Consume the newline
                    }
                } else { // user input is string
                    String userInput = scanner.next();
                    if (userInput.equalsIgnoreCase("b")) {
                        break; // Exit the loop if the user wants to go back
                    } else {
                        System.out.println("Invalid input. Please enter a valid userID or 'b' to go back.");
                        scanner.nextLine();
                    }
                }

            } catch (RemoteException e) {
                System.out.println("Error occurred while checking username existence: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid userID.");
//                System.out.println("Press any key here to continue...");
            }
        }
    }

    public void deleteUserInterface() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        List<User> userList = userService.getAllUsers();
        UserActions userActions = new UserActions();
        Scanner scanner = new Scanner(System.in);

        String[] headers = {"ID", "First Name", "Last Name", "IC/Passport"};
        String prompt = "Enter the ID of the user to delete ('b' for back): ";
        List<String[]> rows = new ArrayList<>();
        List<Integer> optionsID = new ArrayList<>();

        for (User user : userList) {
            rows.add(new String[] {
                    String.valueOf(user.getId()),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getICNum(),
            });
            optionsID.add(user.getId());
        }

        Table table = new Table("A List of User", headers, rows);

        while (true) {
            try {
                table.display();
                if (scanner.hasNextInt()) { // Check the user input is int
                    int selectedUserId = scanner.nextInt();
                    boolean isUserExist = userService.checkUserId(selectedUserId);
                    if (isUserExist) {
                        userDeleteProcess(selectedUserId);
                        break;
                    } else {
                        System.out.println("UserID does not exist.");
                        scanner.nextLine(); // Consume the newline
                    }
                } else { // user input is string
                    String userInput = scanner.next();
                    if (userInput.equalsIgnoreCase("b")) {
                        break; // Exit the loop if the user wants to go back
                    } else {
                        System.out.println("Invalid input. Please enter a valid userID or 'b' to go back.");
                        scanner.nextLine();
                    }
                }

            } catch (RemoteException e) {
                System.out.println("Error occurred while checking username existence: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid userID.");
//                System.out.println("Press any key here to continue...");
            }
        }


    }

    private void userDeleteProcess(int selectedUserId) throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                // Check if the user ID exists
                boolean isUserExist = userService.checkUserId(selectedUserId);
                if (isUserExist) {
                    // Retrieve the user details
                    User userToDelete = userService.getUserById(selectedUserId);

                    // Handle null userToDelete object
                    if (userToDelete == null) {
                        System.out.println("User not found.");
                        return;
                    }

                    // Display user details and confirmation prompt
                    System.out.println("\n------------------------------------------------------------");
                    System.out.println("Are you sure you want to delete the following user?");
                    System.out.println("------------------------------------------------------------");
                    System.out.println("ID: " + userToDelete.getId());
                    System.out.println("First Name: " + userToDelete.getFirstName());
                    System.out.println("Last Name: " + userToDelete.getLastName());
                    System.out.println("IC/Passport: " + userToDelete.getICNum());
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Type 'y' to confirm deletion, 'n' to cancel, or 'b' to go back.");

                    // Prompt for confirmation
                    String confirmation = scanner.next();
                    if (confirmation.equalsIgnoreCase("y")) {
                        // Delete the user
                        boolean deleted = userService.removeUser(userToDelete);
                        if (deleted) {
                            System.out.println("User deleted successfully.");
                        } else {
                            System.out.println("Failed to delete user.");
                        }
                        break; // Exit the loop after deletion
                    } else if (confirmation.equalsIgnoreCase("n")) {
                        System.out.println("Deletion canceled.");
                        break; // Exit the loop if deletion is canceled
                    } else if (confirmation.equalsIgnoreCase("b")) {
                        System.out.println("Returning to previous menu.");
                        return; // Exit the method and return to the previous menu
                    } else {
                        System.out.println("Invalid input. Please enter 'y', 'n', or 'b'.");
                    }
                } else {
                    // Inform the user that the user ID does not exist
                    System.out.println("UserID does not exist.");
                    break; // Exit the loop if the user ID does not exist
                }
            } catch (RemoteException e) {
                System.out.println("Error occurred while deleting user details: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid options.");
                System.out.println("Press any key here to continue...");
                scanner.nextLine();
            }
        }
    }

    private void userUpdateProcess(int selectedUserId) throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        Scanner scanner = new Scanner(System.in);

        // Retrieve original user details
        User userToUpdate = userService.getUserById(selectedUserId);
        if (userToUpdate == null) {
            System.out.println("User not found.");
            return;
        }

        // Display original user details

        // Prompt user to select detail to update
        while (true) {
            try {
                System.out.println("\n------------------------------------------------------------");
                System.out.println("Original User Details:");
                System.out.println("------------------------------------------------------------");
                System.out.println("ID: " + userToUpdate.getId());
                System.out.println("First Name: " + userToUpdate.getFirstName());
                System.out.println("Last Name: " + userToUpdate.getLastName());
                System.out.println("IC/Passport: " + userToUpdate.getICNum());
                System.out.println("------------------------------------------------------------");

                List<String> options = List.of("First Name", "Last Name", "IC/Passport", "Back");
                Menu menu = new Menu("", options, "Select a detail to update: ", 60);
                menu.display();

                switch (menu.getInput()) {
                    case 1:
                        System.out.println("Enter New First Name: ");
                        String newFirstName = scanner.nextLine();
                        userToUpdate.setFirstName(newFirstName);
                        break;
                    case 2:
                        System.out.println("Enter New Last Name: ");
                        String newLastName = scanner.nextLine();
                        userToUpdate.setLastName(newLastName);
                        break;
                    case 3:
                        System.out.println("Enter New IC/Passport: ");
                        String newICNum = scanner.nextLine();
                        userToUpdate.setICNum(newICNum);
                        break;
                    case 4:
                        // Exit the method if user selects "Back"
                        return;
                    default:
                        System.out.println("\nInvalid input. Please try again.");
                        continue;
                }

                // Update the user details
                boolean success = userService.updateUser(userToUpdate);
                if (success) {
                    System.out.println("User details updated successfully.");
                } else {
                    System.out.println("Failed to update user details.");
                }

            } catch (RemoteException e) {
                System.out.println("Error occurred while updating user details: " + e.getMessage());
                System.out.println("Please try again later.");
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid options.");
                System.out.println("Press any key here to continue...");
                scanner.nextLine();
            }
        }
    }


}
