package client.pages;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.RemoteServiceLocator;
import client.components.Form;
import client.pages.admin.AdminInterface;
import client.pages.customer.CustomerInterface;
import models.User;
import models.UserType;
import remote.AuthServiceRemote;

public class LoginInterface {
    public static void start() throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        Form form = new Form();
        form.addStringField("username", "Enter your username: ");
        form.addStringField("password", "Enter your password: ");

        String username = (String) form.getField("username");
        String password = (String) form.getField("password");

        AuthServiceRemote authService = RemoteServiceLocator.getAuthService();
        if (authService == null) { return; }

        User currentUser = authService.authenticate(username, password);

        if (currentUser == null) {
            System.out.println("Invalid credentials. Please try again.");
            Thread.sleep(2000);
        } else if (currentUser.getUserType().equals(UserType.ADMIN)) {
            new AdminInterface(currentUser).start();
        } else {
            new CustomerInterface(currentUser).start();
        }
    }
}
