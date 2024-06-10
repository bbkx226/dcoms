package client.pages;

import client.components.Form;
import models.User;
import models.UserType;
import remote.AuthServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class LoginInterface {
    private final AuthServiceRemote authService;
    private final Scanner scanner;

    public LoginInterface() throws MalformedURLException, NotBoundException, RemoteException {
        this.authService = (AuthServiceRemote) Naming.lookup("rmi://localhost:7777/authService");
        this.scanner = new Scanner(System.in);
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        Form form = new Form();
        form.addField("username", "Enter your username:");
        form.addField("password", "Enter your password:");

        String username = form.getField("username");
        String password = form.getField("password");

        User currentUser = authService.authenticate(username, password);

        if (currentUser == null) {
            System.out.println("Invalid credentials. Please try again.");
            return;
        }

        if (currentUser.getUserType().equals(UserType.CUSTOMER)) {
            CustomerInterface customerInterface = new CustomerInterface();
            customerInterface.customerMenu(currentUser);
        } else { // admin
            AdminInterface adminInterface = new AdminInterface(currentUser);
            adminInterface.start();
        }
    }
}
