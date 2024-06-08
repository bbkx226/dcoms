package server;

import models.Food;
import models.Order;
import remote.OrderServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

// CRUD functions for orders
public class OrderServiceImpl extends UnicastRemoteObject implements OrderServiceRemote {
    private List<Order> orders;
    private FoodServiceImpl foodDAO;

    public OrderServiceImpl() throws RemoteException {
        super();
        orders = new ArrayList<>();
        foodDAO = new FoodServiceImpl();
    }

    @Override
    public boolean addOrder(int foodId, int qty) throws RemoteException {
        Food food = foodDAO.getFoodById(foodId);
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

    @Override
    public List<Order> getOrders() throws RemoteException { return orders; }

    @Override
    public Order getOrderByFoodId(int foodId) throws RemoteException {
        for (Order order : orders) {
            if (order.getFoodId() == foodId) {
                return order;
            }
        }
        return null;
    }

    @Override
    public boolean updateOrder(Order updatedOrder) throws RemoteException {
        Order orderToUpdate = getOrderByFoodId(updatedOrder.getFoodId());
        if (orderToUpdate == null) { return false; }
        if (updatedOrder.getQuantity() > foodDAO.getFoodById(updatedOrder.getFoodId()).getQty()) { return false; }
        Food food = foodDAO.getFoodById(updatedOrder.getFoodId());
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

    @Override
    public boolean checkout() throws RemoteException {
        for (Order order : orders) {
            Food currentFood = foodDAO.getFoodById(order.getFoodId());
            if (currentFood == null || order.getQuantity() > currentFood.getQty()) {
                return false;
            }
            int newFoodQty = currentFood.getQty() - order.getQuantity();
            currentFood.setQty(newFoodQty);
            foodDAO.updateFood(currentFood);
        }
        orders.clear();
        return true;
    }
}
