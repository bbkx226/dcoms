package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import models.User;

// Provides remote authentication service
public interface AuthServiceRemote extends Remote {
    // Authenticates a user with given username and password
    User authenticate(String username, String password) throws RemoteException;
}