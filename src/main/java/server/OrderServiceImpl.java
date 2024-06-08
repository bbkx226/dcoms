package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import models.Food;
import models.Order;
import remote.OrderServiceRemote;

// CRUD functions for orders
public class OrderServiceImpl extends UnicastRemoteObject implements OrderServiceRemote {
    private final List<Order> orders;
    private final FoodServiceImpl foodRepository;

    // Constructor that initializes an empty list of orders and a new FoodServiceImpl object.
    public OrderServiceImpl() throws RemoteException {
        super();
        orders = new ArrayList<>();
        foodRepository = new FoodServiceImpl();
    }

    // Adds a new order for a food item if the food item exists and there is enough quantity.
    @Override
    public boolean addOrder(int foodId, int qty) throws RemoteException {
        Food food = foodRepository.getFoodById(foodId);
        if (food != null && food.getQty() > 0) {
            boolean foodOrderExists = false;
            for (Order order : orders) {
                if (order.getFoodId() == food.getId()) {
                    foodOrderExists = true;
                    int newQty = order.getQuantity() + qty;
                    if (newQty > food.getQty()) { return false; }
                    order.setQuantity(newQty);
                    double newPrice = order.getQuantity() * food.getPrice();
                    order.setPrice(newPrice);
                    break;
                }
            }
            if (!foodOrderExists) {
                if (qty > food.getQty()) { return false; }
                double newPrice = qty * food.getPrice();
                Order newOrder = new Order(foodId, food.getName(), qty, newPrice);
                orders.add(newOrder);
            }
            return true;
        } else return false;
    }

    // Returns the list of all orders.
    @Override
    public List<Order> getOrders() throws RemoteException { return orders; }

    // Returns an order for a specific food item by its ID, or null if no such order exists.
    @Override
    public Order getOrderByFoodId(int foodId) throws RemoteException {
        for (Order order : orders) {
            if (order.getFoodId() == foodId) {
                return order;
            }
        }
        return null;
    }

    // Updates an existing order if it exists and there is enough quantity of the food item.
    @Override
    public boolean updateOrder(Order updatedOrder) throws RemoteException {
        Order orderToUpdate = getOrderByFoodId(updatedOrder.getFoodId());
        if (orderToUpdate == null) { return false; }
        if (updatedOrder.getQuantity() > foodRepository.getFoodById(updatedOrder.getFoodId()).getQty()) { return false; }
        Food food = foodRepository.getFoodById(updatedOrder.getFoodId());
        if (food != null) {
            for (Order order : orders) {
                if (order.getFoodId() == food.getId()) {
                    if (updatedOrder.getQuantity() == 0) {
                        return deleteOrder(updatedOrder);
                    } else {
                        orders.set(orders.indexOf(order), updatedOrder);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // Deletes an existing order by its food ID if it exists.
    @Override
    public boolean deleteOrder(Order orderToRemove) throws RemoteException {
        if (getOrderByFoodId(orderToRemove.getFoodId()) == null) { return false; }
        for (Order order : orders) {
            if (order.getFoodId() == orderToRemove.getFoodId()) {
                return orders.remove(order);
            }
        }
        return false;
    }

    // Processes all orders, updating the quantity of each food item and clearing the list of orders.
    @Override
    public boolean checkout() throws RemoteException {
        for (Order order : orders) {
            Food currentFood = foodRepository.getFoodById(order.getFoodId());
            if (currentFood == null || order.getQuantity() > currentFood.getQty()) {
                return false;
            }
            int newFoodQty = currentFood.getQty() - order.getQuantity();
            currentFood.setQty(newFoodQty);
            foodRepository.updateFood(currentFood);
        }
        orders.clear();
        return true;
    }
}