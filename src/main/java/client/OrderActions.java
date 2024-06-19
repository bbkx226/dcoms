package client;

import models.Food;
import client.components.Table;
import models.Order;
import models.User;
import remote.OrderServiceRemote;
import utils.InputUtils;
import utils.UIUtils;

import java.rmi.RemoteException;
import java.util.List;

public class OrderActions {
    public static void displayOrderDetails(Order order) {
        System.out.println("ID: " + order.getId());
        System.out.println("User ID: " + order.getUserId());
        System.out.println("Food ID: " + order.getFoodId());
        System.out.println("Food Name: " + order.getFoodName());
        System.out.println("Quantity: " + order.getQuantity());
        System.out.println("Price/Unit: " + order.getPrice());
        System.out.println("Total Price: " + order.getTotalPrice());
    }

    public static void displayAllOrders() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        List<Order> orderList = orderService.getOrders();

        String[] headers = {"Order ID", "Customer ID", "Food ID", "Food Name", "Price/Unit", "Quantity", "Total Price"};
        List<String[]> rows = orderList.stream()
                .map(order -> new String[] {
                    String.valueOf(order.getId()),
                    String.valueOf(order.getUserId()),
                    String.valueOf(order.getFoodId()),
                    order.getFoodName(),
                    String.valueOf(order.getPrice()),
                    String.valueOf(order.getQuantity()),
                    String.valueOf(order.getTotalPrice()),
                }).toList();

        Table table = new Table("McGee's Order List", headers, rows);
        table.display();
    }

    public static void displayUserOrders(User user) throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        List<Order> orderList = orderService.getOrders();

        String[] headers = {"Order ID", "Food ID", "Food Name", "Price/Unit", "Quantity", "Total Price"};
        List<String[]> rows = orderList.stream()
                .filter(order -> order.getUserId() == user.getId())
                .map(order -> new String[] {
                        String.valueOf(order.getId()),
                        String.valueOf(order.getFoodId()),
                        order.getFoodName(),
                        String.valueOf(order.getPrice()),
                        String.valueOf(order.getQuantity()),
                        String.valueOf(order.getTotalPrice()),
                }).toList();
        Table table = new Table ("Your Orders", headers, rows);
        table.display();
    }

    public static Order selectOrderById() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return null; }

        int selectedId = InputUtils.intInput("Enter the ID of the order ('b' for back): ", "b");
        if (selectedId == Integer.MIN_VALUE) { return null; }
        Order selectedOrder = orderService.getOrderByOrderId(selectedId);
        if (selectedOrder == null) {
            System.out.println("Order not found in database.");
            InputUtils.waitForAnyKey();
            return null;
        }
        return selectedOrder;
    }

    // Add order function for admin
    public static void addOrder() throws RemoteException {
        // Display users
        UserActions.displayUsers();

        // Step 1: Select a user to place the order
        User selectedUser = UserActions.selectUserById();
        if (selectedUser == null) { return; }

        // Display foods menu
        FoodActions.displayFoods();

        // Step 2: Select the food to order
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

            // select quantity
            int qty = InputUtils.intInput("Quantity: ", "b");
            if (qty == Integer.MIN_VALUE) { return; }
            if (qty < 0) {
                System.out.println("Quantity must be greater than 0.");
                continue;
            }

            // add order in server
            OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
            if (orderService == null) { return; }

            if (orderService.addOrder(selectedUser.getId(), selectedFood.getId(), qty)) {
                System.out.println("Order added successfully!");
                break;
            } else {
                System.out.println("Invalid value. Please check your quantity.");
            }
        }
    }

    public static void updateOrder() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        while (true) {
            // display orders
            displayAllOrders();

            // check if orders are empty
            if (orderService.getOrders().isEmpty()) {
                System.out.println("No data available.");
                InputUtils.waitForAnyKey();
                return;
            }

            // Step 1: select an order
            Order selectedOrder = selectOrderById();
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

    public static void deleteOrder() throws RemoteException {
        OrderServiceRemote orderService = RemoteServiceLocator.getOrderService();
        if (orderService == null) { return; }

        while (true) {
            displayAllOrders();

            // Check if there are no orders and return
            if (orderService.getOrders().isEmpty()) {
                System.out.println("No data available.");
                InputUtils.waitForAnyKey();
                return;
            }

            // Step 1: Select an order
            Order selectedOrder = selectOrderById();
            if (selectedOrder == null) { return; }

            // Display order details
            System.out.println();
            UIUtils.line(60);
            System.out.println("Are you sure you want to delete the following order?");
            UIUtils.line(60);
            displayOrderDetails(selectedOrder);
            UIUtils.line(60);

            // Step 2: Confirmation
            char confirmation = InputUtils.charInput("type 'y' to confirm deletion, 'b' to cancel: ", 'b');
            if (confirmation == '\0') {
                System.out.println("Deletion cancelled."); // exit the method and return to the previous menu
                return;
            }
            if (confirmation == 'y') {
                if (orderService.deleteOrder(selectedOrder)) {
                    System.out.println("Order deleted successfully.");
                } else {
                    System.out.println("Failed to delete order from database.");
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'b'.");
            }
        }
    }
}