package RMI;

import java.rmi.*;
import java.rmi.registry.*;
import java.sql.SQLException;

public class Register {
    private final int portNumber;

    public Register() {
        this.portNumber = 7777;
    }

    public static void main(String[] args) throws RemoteException, SQLException {
        Register Reg = new Register();
        try {
            Registry socketclient = LocateRegistry.createRegistry(Reg.portNumber);
            System.out.println("Port registered at: " + Reg.portNumber);
            socketclient.rebind("DCOMS", new Server());
            System.out.println("Server is running...");
            
        } catch (RemoteException ex) {
            System.out.println(ex);
        }
    }
}
