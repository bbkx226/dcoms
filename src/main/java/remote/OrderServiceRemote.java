package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.Order;

// Provides remote order service
public interface OrderServiceRemote extends Remote {
    // Adds a new order for a food item
    boolean addOrder(int foodId, int qty) throws RemoteException;

    // Returns a list of all orders
    List<Order> getOrders() throws RemoteException;

    // Returns an order by its associated food ID
    Order getOrderByFoodId(int foodId) throws RemoteException;

    // Updates an existing order
    boolean updateOrder(Order updatedOrder) throws RemoteException;

    // Removes an order
    boolean deleteOrder(Order orderToRemove) throws RemoteException;

    // Checks out all orders
    boolean checkout() throws RemoteException;
}