package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FoodOrderInterface extends Remote {
    public boolean login(String username, String password) throws RemoteException;
}
