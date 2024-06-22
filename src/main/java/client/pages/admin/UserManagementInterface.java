package client.pages.admin;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.UserActions;
import client.components.Menu;
import client.pages.RegisterInterface;
import utils.UIUtils;

public class UserManagementInterface {
    public static void start() throws MalformedURLException, NotBoundException, RemoteException {
        List<String> options = List.of("Create User", "Update User", "Delete User", "Back");
        Menu menu = new Menu("Manage Users", options);

        while (true) {
            UIUtils.clearScreen();
            UserActions.displayUsers();
            menu.display();

            switch (menu.getInput("Enter your choice: ")) {
                case 1 -> RegisterInterface.start();
                case 2 -> UserActions.updateUser();
                case 3 -> UserActions.deleteUser();
                case 4 -> {
                    return;
                }
            }
        }
    }
}
