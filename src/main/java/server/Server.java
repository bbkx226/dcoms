package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final int PORT = 7777;

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        try {
            // Create RMI registry
            Registry serverRegister = LocateRegistry.createRegistry(PORT);

            // Bind objects to the registry
            serverRegister.rebind("authService", new AuthServiceImpl());
            serverRegister.rebind("userService", new UserServiceImpl());
            serverRegister.rebind("foodService", new FoodServiceImpl());
            serverRegister.rebind("orderService", new OrderServiceImpl());

            System.out.println("Food order server is running...");
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Server exception: ", e);
        }
    }
}