package remote;

import models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthServiceRemote extends Remote {
    public User authenticate(String username, String password) throws RemoteException;
}
