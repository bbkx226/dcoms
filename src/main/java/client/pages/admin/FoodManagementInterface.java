package client.pages.admin;

import client.FoodActions;
import client.components.Menu;
import utils.UIUtils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class FoodManagementInterface {
    public static void start() throws MalformedURLException, NotBoundException, RemoteException {
        List<String> options = List.of("Add Product", "Update Product", "Delete Product", "Back");
        Menu menu = new Menu("Manage Food", options);

        while (true) {
            UIUtils.clrscr();
            FoodActions.displayFoods();
            menu.display();

            switch (menu.getInput("Enter your choice: ")) {
                case 1:
                    FoodActions.addFood();
                    break;
                case 2:
                    FoodActions.updateFood();
                    break;
                case 3:
                    FoodActions.deleteFood();
                    break;
                case 4:
                    return;
            }
        }
    }
}
