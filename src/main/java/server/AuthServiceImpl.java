package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.User;
import remote.AuthServiceRemote;
import utils.FileUtils;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthServiceRemote {
    private static final Logger LOGGER = Logger.getLogger(AuthServiceImpl.class.getName());

    // Constructor that throws a RemoteException if the creation of the remote object fails.
    public AuthServiceImpl() throws RemoteException {
        super();
    }

    // Method that authenticates a user by checking if the provided username and password match any user in the file.
    @Override
    public User authenticate(String username, String password) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        User authenticatedUser = users.stream()
                                      .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                                      .findFirst()
                                      .orElse(null);
        LOGGER.log(Level.INFO, "Authentication attempt for username: {0} - Success: {1}", new Object[]{username, authenticatedUser != null});
        return authenticatedUser;
    }
}