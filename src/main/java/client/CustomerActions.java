package client;

import models.Food;
import models.Order;
import models.User;
import remote.OrderServiceRemote;
import utils.InputUtils;
import utils.UIUtils;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;


public class CustomerActions {
    private final User user;

    public CustomerActions(User user) {
        this.user = user;
    }

    private Order selectOrderById() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return null; }

        int selectedId = InputUtils.intInput("Enter the ID of the order ('b' for back): ", "b");
        if (selectedId == Integer.MIN_VALUE) { return null; }

        List<Order> userOrderList = orderService.getOrders()
                .stream()
                .filter(order -> order.getUserId() == this.user.getId() && order.getId() == selectedId)
                .toList();

        if (userOrderList.isEmpty()) {
            System.out.println("Your selected order ID was not found in the database.");
            InputUtils.waitForAnyKey();
            return null;
        } else return userOrderList.getFirst();
    }

    // Customer Add Order
    public void addOrder() throws RemoteException {
        // display menu
        FoodActions.displayFoods();

        // Step 1: Select the food to order
        Food selectedFood = FoodActions.selectFoodById();
        if (selectedFood == null) { return; }

        while (true) {
            // Display details of selected food
            System.out.println();
            UIUtils.line(60);
            UIUtils.printHeader("Selected Food Details", 60);
            UIUtils.line(60);
            FoodActions.displayFoodDetails(selectedFood);
            UIUtils.line(60);

            // Step 2: Select quantity
            int qty = InputUtils.intInput("Quantity: ", "b");
            if (qty == Integer.MIN_VALUE) { return; }
            if (qty < 0) {
                System.out.println("Quantity must be greater than 0.");
                continue;
            }

            // Step 3: Add order in server
            OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
            if (orderService == null) { return; }

            if (orderService.addOrder(this.user.getId(), selectedFood.getId(), qty)) {
                System.out.println("Order added successfully!");
                break;
            } else {
                System.out.println("Invalid value. Please check your quantity.");
            }
        }
    }

    public void updateOrder() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        while (true) {
            // display customer's orders
            OrderActions.displayUserOrders(this.user);

            // check if customer's orders are empty
            if (orderService.getOrders().isEmpty()) {
                System.out.println("No data available.");
                InputUtils.waitForAnyKey();
                return;
            }

            // Step 1: select an order
            Order selectedOrder = this.selectOrderById();
            if (selectedOrder == null) { return; }

            // Step 2: select new food to order (can be the same)
            FoodActions.displayFoods();
            Food selectedFood = FoodActions.selectFoodById();
            if (selectedFood == null) { return; }

            while (true) {
                // Display selected food
                System.out.println();
                UIUtils.line(60);
                UIUtils.printHeader("Selected Food Details", 60);
                UIUtils.line(60);
                FoodActions.displayFoodDetails(selectedFood);
                UIUtils.line(60);

                // Step 3: select food quantity
                int qty = InputUtils.intInput("Quantity: ", "b");
                if (qty == Integer.MIN_VALUE) { return; }
                if (qty < 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }

                // Step 4: Set order details to the selected food and quantity
                selectedOrder.setFoodId(selectedFood.getId());
                selectedOrder.setFoodName(selectedFood.getName());
                selectedOrder.setQuantity(qty);
                selectedOrder.setPrice(selectedFood.getPrice());

                // Step 5: Update order to server side
                if (orderService.updateOrder(selectedOrder)) {
                    System.out.println("Order updated successfully!");
                    break; // go back to select another order to update
                } else {
                    System.out.println("Invalid quantity. Please enter a valid quantity.");
                }
            }
        }
    }

    //Customer Delete Order from the Cart
    public void deleteOrder() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        while (true) {
            // Display user's orders
            OrderActions.displayUserOrders(this.user);

            // Check if user has no orders and return if so
            List<Order> userOrderList = orderService.getOrders()
                    .stream()
                    .filter(order -> order.getUserId() == this.user.getId())
                    .toList();

            if (userOrderList.isEmpty()) {
                System.out.println("No data available.");
                InputUtils.waitForAnyKey();
                return;
            }

            // Step 1: Select an order
            Order selectedOrder = this.selectOrderById();
            if (selectedOrder == null) { return; }

            // Display order details
            System.out.println();
            UIUtils.line(60);
            System.out.println("Are you sure you want to delete the following order?");
            UIUtils.line(60);
            OrderActions.displayOrderDetails(selectedOrder);
            UIUtils.line(60);

            // Step 2: Confirmation
            char confirmation = InputUtils.charInput("type 'y' to confirm deletion, 'b' to cancel: ", 'b');
            if (confirmation == '\0') {
                System.out.println("Deletion cancelled.");
                return;
            }
            if (confirmation == 'y') {
                if (orderService.deleteOrder(selectedOrder)) {
                    System.out.println("Order deleted successfully.");
                } else {
                    System.out.println("Failed to delete order from database.");
                }
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'b'.");
            }
        }
    }




    // Customer check order before make payment
    public void checkOrder() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        // Filter orders by customer ID
        List<Order> userOrderList = orderService.getOrders()
                .stream()
                .filter(order -> order.getUserId() == this.user.getId())
                .toList();

        double totalPrice = userOrderList.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        // Check if there are no orders and return if true
        if (userOrderList.isEmpty()) {
            System.out.println("No orders found.");
            InputUtils.waitForAnyKey();
            return;
        }

        // Display order data table
        OrderActions.displayUserOrders(this.user);

        // Display total price
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));

        // Print line after the Total Price
        UIUtils.line(60);

        char confirmation = InputUtils.charInput("Press 'p' to proceed to payment for your orders, or press 'b' to go back.", 'b');
        if (confirmation == '\0') {
            System.out.println("Returning to previous menu.");
            return;
        }
        if (confirmation == 'p' || confirmation == 'P') {
            String paymentConfirmation = InputUtils.stringInput("Are you sure you want to proceed to payment? (yes/y to confirm)", "b");
            if (paymentConfirmation.equalsIgnoreCase("yes") || paymentConfirmation.equalsIgnoreCase("y")) {
                // Check each order for sufficient stock
                List<Order> insufficientStockOrders = new ArrayList<>();
                for (Order order : userOrderList) {
                    Food food = orderService.getFoodById(order.getFoodId());
                    if (food == null || order.getQuantity() > food.getQty()) {
                        insufficientStockOrders.add(order);
                    }
                }

                if (insufficientStockOrders.isEmpty()) {
                    // Deduct the quantities from the food stock and clear the user's orders
                    if (orderService.checkout(this.user)) {
                        System.out.println("Payment successful! Your order has been processed.");
                        InputUtils.waitForAnyKey();
                    } else {
                        System.out.println("Payment failed. Please try again.");
                    }
                } else {
                    System.out.println("Payment failed.");
                    UIUtils.line(60);
                    System.out.println("The following items do not have enough stock: ");

                    for (Order order : insufficientStockOrders) {
                        Food food = orderService.getFoodById(order.getFoodId());
                        System.out.println("Item: " + food.getName() );
                        System.out.println("Available Stock: " + (food != null ? food.getQty() : 0));
                    }

                    // Prompt the customer to delete or update the orders with insufficient stock
                    for (Order insufficientOrder : insufficientStockOrders) {
                        char action = InputUtils.charInput("Would you like to delete (d) or update (u) the quantity of " + insufficientOrder.getFoodName() + "? (d/u): ", 'b');
                        if (action == 'd') {
                            if (orderService.deleteOrder(insufficientOrder)) {
                                System.out.println("Order deleted successfully.");
                                InputUtils.waitForAnyKey();
                                break;
                            } else {
                                System.out.println("Failed to delete order.");
                            }
                        } else if (action == 'u') {
                            while (true) {
                                int newQty = InputUtils.intInput("Enter new quantity for " + insufficientOrder.getFoodName() + ": ", "b");
                                if (newQty == Integer.MIN_VALUE) { return; }
                                Food foodItem = orderService.getFoodById(insufficientOrder.getFoodId());
                                if (newQty > 0 && foodItem != null && newQty <= foodItem.getQty()) {
                                    insufficientOrder.setQuantity(newQty);
                                    if (orderService.updateOrder(insufficientOrder)) {
                                        System.out.println("Order updated successfully.");
                                        InputUtils.waitForAnyKey();
                                        break;
                                    } else {
                                        System.out.println("Failed to update order. Please try again.");
                                        InputUtils.waitForAnyKey();
                                    }
                                } else {
                                    System.out.println("Invalid quantity. Please enter a valid quantity.");
                                }
                            }
                        } else {
                            System.out.println("Invalid input. Skipping this item.");
                        }
                    }
                }
            } else {
                System.out.println("Payment cancelled.");
            }
        } else {
            System.out.println("Payment cancelled.");
        }
    }


}
