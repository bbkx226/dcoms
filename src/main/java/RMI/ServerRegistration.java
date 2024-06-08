package RMI;

import java.rmi.*;
import java.rmi.registry.*;
import java.sql.SQLException;

public class ServerRegistration {
    private final int serverPort;

    public ServerRegistration() {
        this.serverPort = 7777;
    }

    public static void main(String[] args) throws RemoteException, SQLException {
        ServerRegistration serverReg = new ServerRegistration();
        try {
            Registry registry = LocateRegistry.createRegistry(serverReg.serverPort);
            System.out.println("Server port registered at: " + serverReg.serverPort);
            registry.rebind("DCOMS", new Server());
            System.out.println("Server is now active...");
            
        } catch (RemoteException e) {
            System.out.println("RemoteException occurred: " + e);
        }
    }
}
