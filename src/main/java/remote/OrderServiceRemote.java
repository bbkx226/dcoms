package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.Food;
import models.Order;
import models.User;

// Provides remote order service
public interface OrderServiceRemote extends Remote {
    boolean addOrder(int userId, int foodId, int qty) throws RemoteException; // Adds a new order for a food item
    List<Order> getOrders() throws RemoteException; // Returns a list of all orders
    Order getOrderByOrderId(int orderId) throws RemoteException; // Returns an order by its associated food ID
    boolean updateOrder(Order updatedOrder) throws RemoteException; // Updates an existing order
    boolean deleteOrder(Order orderToRemove) throws RemoteException; // Removes an order
    boolean checkout(User user) throws RemoteException; // Checks out all orders
    Food getFoodById(int foodId) throws RemoteException; // Returns a food item by its ID
}