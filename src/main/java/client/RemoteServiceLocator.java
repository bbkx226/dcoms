package client;

import remote.AuthServiceRemote;
import remote.FoodServiceRemote;
import remote.OrderServiceRemote;
import remote.UserServiceRemote;
import utils.InputUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RemoteServiceLocator {

    public static AuthServiceRemote getAuthService() {
        try {
            return (AuthServiceRemote) Naming.lookup("rmi://localhost:7777/authService");
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("An error occurred while accessing the auth service. Please try again later: " + e.getMessage());
            return null;
        }
    }

    public static UserServiceRemote getUserService() {
        try {
            return (UserServiceRemote) Naming.lookup("rmi://localhost:7777/userService");
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("An error occurred while accessing the user service. Please try again later: " + e.getMessage());
            return null;
        }
    }

    public static FoodServiceRemote getFoodService() {
        try {
            return (FoodServiceRemote) Naming.lookup("rmi://localhost:7777/foodService");
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("An error occurred while accessing the food service. Please try again later: " + e.getMessage());
            InputUtils.waitForAnyKey();
            return null;
        }
    }

    public static OrderServiceRemote getOrderService() {
        try {
            return (OrderServiceRemote) Naming.lookup("rmi://localhost:7777/orderService");
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("An error occurred while accessing the order service. Please try again later: " + e.getMessage());
            InputUtils.waitForAnyKey();
            return null;
        }
    }
}
