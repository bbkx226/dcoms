package remote;

import models.Food;
import models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FoodDAORemote extends Remote {
    public boolean addFood(String newName, int newQty, double newPrice) throws RemoteException;
    public List<Food> getAllFoods() throws RemoteException;
    public Food getFoodById(int foodId) throws RemoteException;
    public boolean updateFood(Food updatedFood) throws RemoteException;
    public boolean removeFood(int id) throws RemoteException;
}
