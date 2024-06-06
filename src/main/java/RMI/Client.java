package RMI;

import java.net.*;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static Interface Object;
    public Client() {
        try {
            Object = (Interface) Naming.lookup("rmi://localhost:7777/DCOMS_ASM");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}