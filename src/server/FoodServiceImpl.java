package server;

import models.Food;
import remote.FoodServiceRemote;
import utils.FileUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class FoodServiceImpl extends UnicastRemoteObject implements FoodServiceRemote {
    public FoodServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean addFood(String newName, int newQty, double newPrice) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        for (Food food : foods) {
            if (newName.equals(food.getName())) {
                return false;
            }
        }
        int newId = foods.toArray().length + 1;
        Food newFood = new Food(newId, newName, newQty, newPrice);
        FileUtils.appendToFile(FileUtils.FileType.FOOD, newFood, Food::toString);
        return true;
    }

    @Override
    public List<Food> getAllFoods() throws RemoteException {
        return FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
    }

    @Override
    public Food getFoodById(int foodId) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        if (foods.toArray()[foodId - 1] != null) {
            return (Food) foods.toArray()[foodId - 1];
        } else return null;
    }

    @Override
    public boolean updateFood(Food updatedFood) throws RemoteException {
        if (getFoodById(updatedFood.getId()) != null) {
            List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
            foods.set(updatedFood.getId() - 1, updatedFood);
            FileUtils.updateFile(FileUtils.FileType.FOOD, foods, Food::toString);
            return true;
        } else return false;
    }

    @Override
    public boolean removeFood(int foodId) throws RemoteException {
        if (getFoodById(foodId) != null) {
            FileUtils.deleteFromFile(FileUtils.FileType.FOOD, foodId);
            return true;
        } else return false;
    }
}
