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
        List<String> usernames = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            usernames.add(user.getUsername());
        }

        UIUtils.line(50);
        UIUtils.printHeader("Register for a McGee account (Press 'b' to cancel)", 50);
        UIUtils.line(50);

        Form form = new Form();
        if (!form.addStringField("firstName", "First Name: ")) { return; }
        if (!form.addStringField("lastName", "Last Name: ")) { return; }
        if (!form.addStringField("ICNum", "IC/Passport Number: ")) { return; }
        if (!form.addStringField("username", "Username: ", usernames::contains, "Username already exists. Please try again.")) { return; }
        if (!form.addStringField("password", "Password: ")) { return; }

        String firstName = (String) form.getField("firstName");
        String lastName = (String) form.getField("lastName");
        String ICNum = (String) form.getField("ICNum");
        String username = (String) form.getField("username");
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
