package client.pages;


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
import java.util.Scanner;

public class RegisterInterface {
    private UserServiceRemote userService;

    public RegisterInterface() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            userService = (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        } catch (RemoteException e) {
            System.out.println("Error occurred while trying to fetch user data.");
            System.out.println("Please try again later.");
        }
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        UIUtils.line(50);
        UIUtils.printHeader("McGee Register", 50);
        UIUtils.line(50);

        Form form = new Form();
        form.addField("firstName", "First Name: ");
        form.addField("lastName", "Last Name: ");
        form.addField("ICNum", "IC/Passport Number: ");
        List<String> usernames = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            usernames.add(user.getUsername());
        }
        form.addField("username", "Username: ", usernames, "Username already exists. Please try again.");
        form.addField("password", "Password: ");

        String firstName = form.getField("firstName");
        String lastName = form.getField("lastName");
        String ICNum = form.getField("ICNum");
        String username = form.getField("username");
        String password = form.getField("password");

        boolean isUserAdded = userService.addUser(firstName, lastName, ICNum, username, password);
        if (isUserAdded) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("User was not added.");
            System.out.println("Please try again later.");
        }
    }
}
