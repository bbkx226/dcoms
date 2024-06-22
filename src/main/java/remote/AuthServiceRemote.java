package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import models.User;

// Provides remote authentication service
public interface AuthServiceRemote extends Remote {
    User authenticate(String username, String password) throws RemoteException; // Authenticates a user with given username and password
}