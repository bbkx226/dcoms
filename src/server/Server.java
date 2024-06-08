package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        // Create RMI registry
        Registry reg = LocateRegistry.createRegistry(7777);

        // bind objects
        reg.rebind("authService", new AuthServiceImpl());
        reg.rebind("userDAOService", new UserDAOImpl());
        reg.rebind("foodDAOService", new FoodDAOImpl());
        reg.rebind("orderService", new OrderServiceImpl());

        System.out.println("Food order service is running...");
    }
}
