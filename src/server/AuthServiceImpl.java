package server;

import models.User;
import remote.AuthServiceRemote;
import utils.FileUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthServiceRemote {
    public AuthServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public User authenticate(String username, String password) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
