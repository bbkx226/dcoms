package models;

import java.io.Serializable;

// Represents an order in the system
public class Order implements Serializable {
    private final int foodId;
    private final String foodName;
    private int quantity;
    private double price;

    // Constructor to initialize the order
    public Order(int foodId, String foodName, int quantity, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public int getFoodId() { return foodId; } // Returns the ID of the food item in the order
    public String getFoodName() { return foodName; } // Returns the name of the food item in the order
    public int getQuantity() { return quantity; } // Returns the quantity of the food item in the order
    public void setQuantity(int qty) { this.quantity = qty; } // Sets the quantity of the food item in the order
    public double getPrice() { return price; } // Returns the price of the food item in the order
    public void setPrice(double price) { this.price = price; } // Sets the price of the food item in the order
    public double getTotalPrice() { return quantity * price; } // Returns the total price of the order

    // Converts the order to a string
    @Override
    public String toString() {
        return "Order{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}