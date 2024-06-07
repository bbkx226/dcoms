package server;

import models.User;
import remote.FoodOrderInterface;
import utils.FileUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements FoodOrderInterface {
    public Server() throws RemoteException {
        super();
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
