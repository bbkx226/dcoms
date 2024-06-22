package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.pages.MainMenu;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        new MainMenu().start();
    }
}
