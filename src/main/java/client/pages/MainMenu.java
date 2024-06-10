package client.pages;

import client.FoodActions;
import client.components.Menu;
import client.components.Table;
import models.*;
import remote.FoodServiceRemote;
import utils.InputUtils;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private final Menu menu;

    public MainMenu() {
        List<String> options = List.of("Login", "View Menu", "Register", "Exit");
        this.menu = new Menu("Welcome to McGee!", options, "Enter your choice: ");
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        while (true) {
            UIUtils.clrscr();
            menu.display();

            switch (menu.getInput()) {
                case 1:
                    new LoginInterface().start();
                    break;
                case 2:
                    FoodActions.viewMenu();
                    break;
                case 3:
                    new RegisterInterface().start();
                    break;
                case 4:
                    exitApp();
            }
        }
    }

    private static void exitApp() {
        System.out.println("Thank you for using McGee! Goodbye!");
        System.exit(1);
    }
}
