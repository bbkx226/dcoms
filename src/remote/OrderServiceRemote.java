package remote;

import models.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface OrderServiceRemote extends Remote {
    public boolean addOrder(int foodId, int qty) throws RemoteException;
    public List<Order> getOrders() throws RemoteException;
    public Order getOrderByFoodId(int foodId) throws RemoteException;
    public boolean updateOrder(Order updatedOrder) throws RemoteException;
    public boolean deleteOrder(Order orderToRemove) throws RemoteException;
    public boolean checkout() throws RemoteException;
}
