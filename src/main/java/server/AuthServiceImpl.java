package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import models.User;
import remote.AuthServiceRemote;
import utils.FileUtils;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthServiceRemote {
    // Constructor that throws a RemoteException if the creation of the remote object fails.
    public AuthServiceImpl() throws RemoteException {
        super();
    }

    // Method that authenticates a user by checking if the provided username and password match any user in the file.
    @Override
    public User authenticate(String username, String password) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        return users.stream()
                    .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);
    }
}
