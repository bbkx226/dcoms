package client.pages;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import client.FoodActions;
import client.components.Menu;
import utils.InputUtils;
import utils.UIUtils;

public class MainMenu {
    private final Menu menu;

    public MainMenu() {
        List<String> options = List.of("Login", "View Menu", "Register", "Exit");
        this.menu = new Menu("Welcome to McGee!", options);
    }

    public void start() throws MalformedURLException, NotBoundException, RemoteException {
        while (true) {
            UIUtils.clearScreen();
            menu.display();

            switch (menu.getInput("Enter your choice: ")) {
                case 1:
                    LoginInterface.start();
                    break;
                case 2:
                    FoodActions.displayFoods();
                    InputUtils.waitForAnyKey();
                    break;
                case 3:
                    new RegisterInterface().start();
                    break;
                case 4:
                    System.out.println("Thank you for using McGee! Goodbye!");
                    System.exit(1);
            }
        }
    }
}
