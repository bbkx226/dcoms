package client.pages;

import client.UserActions;
import client.components.Menu;
import client.components.Table;
import models.User;
import remote.UserServiceRemote;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManagementInterface {
    private final UserServiceRemote userService;

    public UserManagementInterface() throws MalformedURLException, NotBoundException, RemoteException {
        this.userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        List<User> userList = userService.getAllUsers();
        UserActions userActions = new UserActions();
        Scanner scanner = new Scanner(System.in);

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
        Table table = new Table("List of Users", headers, rows);

        List<String> options = List.of("Create User", "Update User", "Delete User", "Back");
        Menu menu = new Menu("Manage Users", options, "Enter your choice:", 60);

        UIUtils.clrscr();
        table.display();
        menu.display();

        switch (menu.getInput()) {
            case 1:
                userActions.createUser();
                break;
            case 2:
                userActions.updateUserInterface();
                break;
            case 3:
                userActions.deleteUserInterface();
                break;
            case 4:
                return;
            default:
                System.out.println("\nInvalid input. Please try again.");
        }
    }

}
