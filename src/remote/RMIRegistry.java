package remote;

import server.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRegistry {
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(7777);
        reg.rebind("foodorder", new Server());
        System.out.println("Food order service is running...");
    }
}
