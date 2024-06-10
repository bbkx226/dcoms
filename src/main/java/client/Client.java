package client;

import client.pages.MainMenu;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        // call Login register menu
        MainMenu mainMenu = new MainMenu();
        mainMenu.start();
    }
}
