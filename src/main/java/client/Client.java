package client;

import models.Food;
import models.Order;
import models.User;
import remote.AuthServiceRemote;
import remote.FoodServiceRemote;
import remote.OrderServiceRemote;
import remote.UserServiceRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        // call Login register menu
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.loginMenu();

    }



}
