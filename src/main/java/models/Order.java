package models;

import java.io.Serializable;
import java.text.DecimalFormat;

// Represents an order in the system
public class Order implements Serializable {
    private int id;
    private int foodId;
    private String foodName;
    private final int userId; // New field to store the ID of the user who placed the order
    private int quantity;
    private double price;

    // Constructor to initialize the order
    public Order(int id, int foodId, String foodName, int userId, int quantity, double price) {
        this.id = id;
        this.foodId = foodId;
        this.userId = userId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; } // Returns the ID of the order
    public int getFoodId() { return foodId; } // Returns the ID of the food item in the order
    public String getFoodName() { return foodName; } // Returns the name of the food item in the order
    public int getUserId() { return userId; } // Returns the ID of the user who placed the order
    public int getQuantity() { return quantity; } // Returns the quantity of the food item in the order
    public void setQuantity(int qty) { this.quantity = qty; } // Sets the quantity of the food item in the order
    public void setId(int id) { this.id = id; } // Sets the quantity of the food item in the order
    public void setFoodId(int foodId) { this.foodId = foodId; } // Sets the quantity of the food item in the order
    public void setFoodName(String foodName) { this.foodName = foodName; } // Sets the quantity of the food item in the order
    public double getPrice() { DecimalFormat df = new DecimalFormat("#.##"); // Define the decimal format to round to 2 decimal places
        return Double.parseDouble(df.format(price)); } // Returns the price of the food item in the order
    public void setPrice(double price) { this.price = price; } // Sets the price of the food item in the order
    public double getTotalPrice() { DecimalFormat df = new DecimalFormat("#.##"); // Define the decimal format to round to 2 decimal places
        return Double.parseDouble(df.format(quantity * price)); } // Returns the total price of the order

    // Converts the order to a string
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", foodId=" + foodId +
                ", userId=" + userId +
                ", foodName='" + foodName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}