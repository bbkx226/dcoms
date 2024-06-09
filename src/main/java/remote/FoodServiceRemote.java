package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.Food;

// Provides remote food service
public interface FoodServiceRemote extends Remote {
    // Adds a new food item
    boolean addFood(String newName, int newQty, double newPrice) throws RemoteException;

    // Returns a list of all food items
    List<Food> getAllFoods() throws RemoteException;

    // Returns a food item by its ID
    Food getFoodById(int foodId) throws RemoteException;

    // Updates an existing food item
    boolean updateFood(Food updatedFood) throws RemoteException;

    // Removes a food item by its ID
    boolean removeFood(int id) throws RemoteException;

    // check food is existing?
    boolean checkIsFoodExisted(String newFood) throws RemoteException;

    boolean checkExistedFoodId(int foodId) throws RemoteException;


}