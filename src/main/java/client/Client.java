package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.pages.MainMenu;

public class Client {
    public static void main(String[] args) {
        try {
            new MainMenu().start(); // Initialize and start the main menu
        } catch (MalformedURLException e) {
            System.err.println("URL is malformed: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("Not bound: " + e.getMessage());
        } catch (RemoteException e) {
            System.err.println("Remote exception: " + e.getMessage());
        }
    }
}