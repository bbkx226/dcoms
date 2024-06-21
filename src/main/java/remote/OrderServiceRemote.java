package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.Food;
import models.Order;
import models.User;

// Provides remote order service
public interface OrderServiceRemote extends Remote {
    // Adds a new order for a food item
    boolean addOrder(int userId, int foodId, int qty) throws RemoteException;

    // Returns a list of all orders
    List<Order> getOrders() throws RemoteException;

    // Returns an order by its associated food ID
    Order getOrderByOrderId(int orderId) throws RemoteException;

    // Updates an existing order
    boolean updateOrder(Order updatedOrder) throws RemoteException;

    // Removes an order
    boolean deleteOrder(Order orderToRemove) throws RemoteException;

    // Checks out all orders
    boolean checkout(User user) throws RemoteException;

    // Returns a food item by its ID
    Food getFoodById(int foodId) throws RemoteException;
}