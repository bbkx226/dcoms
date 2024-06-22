package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Food;
import remote.FoodServiceRemote;
import utils.FileUtils;

public class FoodServiceImpl extends UnicastRemoteObject implements FoodServiceRemote {
    private static final Logger LOGGER = Logger.getLogger(FoodServiceImpl.class.getName());

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
        if (foods.stream().anyMatch(food -> newName.equals(food.getName()))) {
            LOGGER.log(Level.INFO, "Add food attempt for {0} failed: Food already exists.", newName);
            return false;
        }

        Food newFood = new Food(newId, newName, newQty, newPrice);
        FileUtils.appendToFile(FileUtils.FileType.FOOD, newFood, Food::toString);
        LOGGER.log(Level.INFO, "Food added successfully: {0}", newName);
        return true;
    }

    // Method that retrieves all food items from the list.
    @Override
    public List<Food> getAllFoods() throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        LOGGER.log(Level.INFO, "Retrieved all foods. Count: {0}", foods.size());
        return foods;
    }

    // Method that retrieves a specific food item by its ID from the list.
    @Override
    public Food getFoodById(int foodId) throws RemoteException {
        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        for (Food food : foods) {
            if (food.getId() == foodId) {
                LOGGER.log(Level.INFO, "Food retrieved by ID: {0}", foodId);
                return food;
            }
        }
        LOGGER.log(Level.INFO, "Food not found by ID: {0}", foodId);
        return null;
    }

    // Method that updates a specific food item in the list if it exists.
    @Override
    public boolean updateFood(Food updatedFood) throws RemoteException {
        if (getFoodById(updatedFood.getId()) == null) {
            LOGGER.log(Level.INFO, "Update food attempt failed: Food not found with ID {0}", updatedFood.getId());
            return false;
        }

        List<Food> foods = FileUtils.readFromFile(FileUtils.FileType.FOOD, Food::fromString);
        for (int i = 0; i < foods.toArray().length; i++) {
            if (updatedFood.getId() == foods.get(i).getId()) {
                foods.set(i, updatedFood);
            }
        }
        FileUtils.updateFile(FileUtils.FileType.FOOD, foods, Food::toString);
        LOGGER.log(Level.INFO, "Food updated successfully: {0}", updatedFood.getName());
        return true;
    }

    // Method that removes a specific food item by its ID from the list if it exists.
    @Override
    public boolean removeFood(int foodId) throws RemoteException {
        Food food = getFoodById(foodId);
        if (food != null) {
            FileUtils.deleteFromFile(FileUtils.FileType.FOOD, foodId);
            LOGGER.log(Level.INFO, "Food removed successfully: ID {0}", foodId);
            return true;
        } else {
            LOGGER.log(Level.INFO, "Remove food attempt failed: Food not found with ID {0}", foodId);
            return false;
        }
    }
}