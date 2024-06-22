package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Food;
import models.Order;
import models.User;
import remote.OrderServiceRemote;

// CRUD functions for orders
public class OrderServiceImpl extends UnicastRemoteObject implements OrderServiceRemote {
    private final List<Order> orders;
    private final FoodServiceImpl foodRepository;
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class.getName());

    // Constructor that initializes an empty list of orders and a new FoodServiceImpl object.
    public OrderServiceImpl() throws RemoteException {
        super();
        orders = new ArrayList<>();
        foodRepository = new FoodServiceImpl();
    }

    // Adds a new order for a food item if the food item exists and there is enough quantity.
    @Override
    public boolean addOrder(int userId, int foodId, int qty) throws RemoteException {
        Food food = foodRepository.getFoodById(foodId);
        if (food != null && food.getQty() > 0) {
            boolean orderExists = false;
            for (Order order : orders) {
                if (order.getFoodId() == food.getId() && order.getUserId() == userId) { // Check userId as well
                    orderExists = true;
                    int newQty = order.getQuantity() + qty;
                    if (newQty > food.getQty()) { 
                        LOGGER.log(Level.INFO, String.format("Add order failed: Not enough quantity for food ID %d", foodId));
                        return false; 
                    }
                    order.setQuantity(newQty);
                    break;
                }
            }
            if (!orderExists) {
                if (qty > food.getQty()) { 
                    LOGGER.log(Level.INFO, String.format("Add order failed: Not enough quantity for food ID %d", foodId));
                    return false; 
                }
                int maxId = orders.stream().mapToInt(Order::getId).max().orElse(0);
                Order newOrder = new Order(maxId + 1, foodId, food.getName(), userId, qty, food.getPrice()); // Ensure unique ID
                orders.add(newOrder);
            }
            LOGGER.log(Level.INFO, String.format("Order added successfully for user ID %d and food ID %d", userId, foodId));
            return true;
        } else {
            LOGGER.log(Level.INFO, String.format("Add order failed: Food ID %d not found or quantity is 0", foodId));
            return false;
        }
    }

    // Returns the list of all orders.
    @Override
    public List<Order> getOrders() throws RemoteException { 
        LOGGER.log(Level.INFO, "Retrieved all orders");
        return orders; 
    }

    // Returns an order for a specific food item by its ID, or null if no such order exists.
    @Override
    public Order getOrderByOrderId(int orderId) throws RemoteException {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                LOGGER.log(Level.INFO, String.format("Order retrieved by order ID: %d", orderId));
                return order;
            }
        }
        LOGGER.log(Level.INFO, String.format("Order not found by order ID: %d", orderId));
        return null;
    }

    // Updates an existing order if it exists and there is enough quantity of the food item.
    @Override
    public boolean updateOrder(Order updatedOrder) throws RemoteException {
        Order orderToUpdate = getOrderByOrderId(updatedOrder.getId());
        if (orderToUpdate == null) { 
            LOGGER.log(Level.INFO, String.format("Update order failed: Order ID %d not found", updatedOrder.getId()));
            return false; 
        }
        Food food = foodRepository.getFoodById(updatedOrder.getFoodId());
        if (food == null) { 
            LOGGER.log(Level.INFO, String.format("Update order failed: Food ID %d not found", updatedOrder.getFoodId()));
            return false; 
        }

        if (updatedOrder.getQuantity() > food.getQty()) { 
            LOGGER.log(Level.INFO, String.format("Update order failed: Not enough quantity for food ID %d", updatedOrder.getFoodId()));
            return false; 
        }
        // Find and update the order in the list
        for (Order order : orders) {
            if (order.getId() != updatedOrder.getId()) { continue; }
            if (updatedOrder.getQuantity() == 0) {
                return deleteOrder(updatedOrder);
            } else {
                orders.set(orders.indexOf(order), updatedOrder);
                LOGGER.log(Level.INFO, String.format("Order updated successfully for order ID %d", updatedOrder.getId()));
                return true;
            }
        }
        return false;
    }

    // Deletes an existing order by its food ID if it exists.
    @Override
    public boolean deleteOrder(Order orderToRemove) throws RemoteException {
        if (getOrderByOrderId(orderToRemove.getId()) == null) { 
            LOGGER.log(Level.INFO, String.format("Delete order failed: Order ID %d not found", orderToRemove.getId()));
            return false; 
        }
        for (Order order : orders) {
            if (order.getId() == orderToRemove.getId()) {
                orders.remove(order);
                LOGGER.log(Level.INFO, String.format("Order deleted successfully for order ID %d", orderToRemove.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkout(User user) throws RemoteException {
        // Filter orders for the current user
        List<Order> userOrders = orders.stream()
                .filter(order -> order.getUserId() == user.getId())
                .toList();

        for (Order order : userOrders) {
            Food currentFood = foodRepository.getFoodById(order.getFoodId());
            if (currentFood == null || order.getQuantity() > currentFood.getQty()) {
                LOGGER.log(Level.INFO, String.format("Checkout failed for user ID %d: Food ID %d not found or not enough quantity", user.getId(), order.getFoodId()));
                return false;
            }
            int newFoodQty = currentFood.getQty() - order.getQuantity();
            currentFood.setQty(newFoodQty);
            if (newFoodQty == 0) {
                foodRepository.removeFood(currentFood.getId());
            } else foodRepository.updateFood(currentFood);
        }

        // Remove the processed orders from the original list
        orders.removeAll(userOrders);
        LOGGER.log(Level.INFO, String.format("Checkout successful for user ID %d", user.getId()));
        return true;
    }

    // Returns a food item by its ID
    @Override
    public Food getFoodById(int foodId) throws RemoteException {
        LOGGER.log(Level.INFO, String.format("Food retrieved by food ID: %d", foodId));
        return foodRepository.getFoodById(foodId);
    }

}