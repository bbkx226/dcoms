package client.pages;


import client.RemoteServiceLocator;
import client.components.Form;
import models.User;
import remote.UserServiceRemote;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterInterface {
    public static void start() throws MalformedURLException, NotBoundException, RemoteException {
        UserServiceRemote userService = RemoteServiceLocator.getUserService();
        if (userService == null) { return; }

        List<String> usernames = userService.getAllUsers()
                .stream()
                .map(User::getUsername)
                .toList();

        UIUtils.line(50);
        UIUtils.printHeader("Register for a McGee account (Press 'b' to cancel)", 50);
        UIUtils.line(50);

        Form form = new Form();
        if (!form.addStringField("firstName", "First Name: ")) { return; }
        if (!form.addStringField("lastName", "Last Name: ")) { return; }
        if (!form.addStringField("ICNum", "IC/Passport Number: ")) { return; }
        String username;
        while (true) {
            if (!form.addStringField("username", "Username: ")) { return; }
            username = (String) form.getField("username");
            if (usernames.contains(username)) {
                System.out.println("Username already exists. Please try again.");
            } else break;
        }
        if (!form.addStringField("password", "Password: ")) { return; }

        String firstName = (String) form.getField("firstName");
        String lastName = (String) form.getField("lastName");
        String ICNum = (String) form.getField("ICNum");
        String password = (String) form.getField("password");

        boolean isUserAdded = userService.addUser(firstName, lastName, ICNum, username, password);
        if (isUserAdded) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("User was not added.");
            System.out.println("Please try again later.");
        }
    }
}
