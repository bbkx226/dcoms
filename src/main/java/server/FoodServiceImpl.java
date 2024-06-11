package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import models.Food;
import models.User;
import remote.FoodServiceRemote;
import utils.FileUtils;

public class FoodServiceImpl extends UnicastRemoteObject implements FoodServiceRemote {
    // Constructor that throws a RemoteException if the creation of the remote object fails.
    public FoodServiceImpl() throws RemoteException {
        super();
    }

    // Method that adds a new food item to the list if it doesn't already exist.
    @Override
    public boolean addFood(String newName, int newQty, double newPrice) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        int maxId = foods.isEmpty() ? 0 : foods.stream().mapToInt(Food::getId).max().orElse(0);
        int newId = maxId + 1;
        if (foods.stream().anyMatch(food -> newName.equals((food.getName())))) {
            return false;
        }

        Food newFood = new Food(newId, newName, newQty, newPrice);
        FileUtils.appendToFile(FileUtils.FileType.FOOD, newFood, Food::toString);
        return true;
    }

    // Method that retrieves all food items from the list.
    @Override
    public List<Food> getAllFoods() throws RemoteException {
        return FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
    }

    // Method that retrieves a specific food item by its ID from the list.
    @Override
    public Food getFoodById(int foodId) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        for (Food food : foods) {
            if (food.getId() == foodId) {
                return food;
            }
        }
        return null;
    }

    // Method that updates a specific food item in the list if it exists.
    @Override
    public boolean updateFood(Food updatedFood) throws RemoteException {
        Food food = getFoodById(updatedFood.getId());
        if (food != null) {
            List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
            foods.set(updatedFood.getId() - 1, updatedFood);
            FileUtils.updateFile(FileUtils.FileType.FOOD, foods, Food::toString);
            return true;
        } else return false;
    }

    // Method that removes a specific food item by its ID from the list if it exists.
    @Override
    public boolean removeFood(int foodId) throws RemoteException {
        Food food = getFoodById(foodId);
        if (food != null) {
            FileUtils.deleteFromFile(FileUtils.FileType.FOOD, foodId);
            return true;
        } else return false;
    }

    // Method that when creating food before check the food item if exists
    @Override
    public boolean checkIsFoodExisted(String newFood) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        return foods.stream().anyMatch(food -> newFood.equals(food.getName()));
    }

    // Method that check the food if exists
    @Override
    public boolean checkExistedFoodId(int foodId) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        return foods.stream().anyMatch(food -> Integer.valueOf(foodId).equals(food.getId()));
    }
}