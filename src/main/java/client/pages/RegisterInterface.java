package client.pages;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.RemoteServiceLocator;
import client.components.Form;
import models.User;
import remote.UserServiceRemote;
import utils.UIUtils;

public class RegisterInterface {
    public static void start() throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        UserServiceRemote userService = RemoteServiceLocator.getUserService();
        if (userService == null) return;

        List<String> usernames = userService.getAllUsers()
                .stream()
                .map(User::getUsername)
                .toList();

        UIUtils.printLine(60);
        UIUtils.printHeader("Register for a McGee account (Press 'b' to go back)", 60);
        UIUtils.printLine(60);

        Form form = new Form();
        if (!form.addStringField("firstName", "First Name: ")) return;
        if (!form.addStringField("lastName", "Last Name: ")) return;
        if (!form.addStringField("ICNum", "IC/Passport Number: ")) return;
        String username;
        while (true) {
            if (!form.addStringField("username", "Username: ")) return;
            username = (String) form.getField("username");

            if (usernames.contains(username)) System.out.println("Username already exists. Please try again.");
            else break;
        }

        if (!form.addStringField("password", "Password: ")) return;

        String firstName = (String) form.getField("firstName");
        String lastName = (String) form.getField("lastName");
        String ICNum = (String) form.getField("ICNum");
        String password = (String) form.getField("password");

        boolean isUserAdded = userService.addUser(firstName, lastName, ICNum, username, password);
        if (isUserAdded) {
            System.out.println("\nUser added successfully! Proceeding back to the main menu...");
            Thread.sleep(1000);
        } else {
            System.out.println("\nUser was not added.");
            System.out.println("Please try again later.");
        }
    }
}
