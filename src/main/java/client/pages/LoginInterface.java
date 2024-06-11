package client.pages;

import client.components.Form;
import models.User;
import models.UserType;
import remote.AuthServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginInterface {
    private final AuthServiceRemote authService;

    public LoginInterface() throws MalformedURLException, NotBoundException, RemoteException {
        this.authService = (AuthServiceRemote) Naming.lookup("rmi://localhost:7777/authService");
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        Form form = new Form();
        form.addStringField("username", "Enter your username: ");
        form.addStringField("password", "Enter your password: ");

        String username = (String) form.getField("username");
        String password = (String) form.getField("password");

        User currentUser = authService.authenticate(username, password);

        if (currentUser == null) {
            System.out.println("Invalid credentials. Please try again.");
            return;
        }

        if (currentUser.getUserType().equals(UserType.CUSTOMER)) {
            CustomerInterface customerInterface = new CustomerInterface();
            customerInterface.customerMenu(currentUser);
        } else new AdminInterface(currentUser).start();
    }
}
