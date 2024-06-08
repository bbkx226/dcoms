package RMI;

import java.net.*;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient {
    public static Interface remoteObject;
    public RMIClient() {
        try {
            remoteObject = (Interface) Naming.lookup("rmi://localhost:7777/DCOMS_ASM");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, "An unexpected error occurred", ex);
        }
    }
}