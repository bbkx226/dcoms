package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.Food;

// Provides remote food service
public interface FoodServiceRemote extends Remote {
    boolean addFood(String newName, int newQty, double newPrice) throws RemoteException; // Adds a new food item
    List<Food> getAllFoods() throws RemoteException; // Returns a list of all food items
    Food getFoodById(int foodId) throws RemoteException; // Returns a food item by its ID
    boolean updateFood(Food updatedFood) throws RemoteException; // Updates an existing food item
    boolean removeFood(int id) throws RemoteException; // Removes a food item by its ID
}