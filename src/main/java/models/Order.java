package models;

import java.io.Serializable;
import java.text.DecimalFormat;

// Represents an order in the system
public class Order implements Serializable {
    private final int userId; // New field to store the ID of the user who placed the order
    private String foodName;
    private int id;
    private int foodId;
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
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public int getUserId() { return userId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return roundToTwoDecimalPlaces(price); }
    public void setPrice(double price) { this.price = price; }

    public double getTotalPrice() { return roundToTwoDecimalPlaces(quantity * price); }

    private double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }
    
    // Converts the order to a string
    @Override
    public String toString() {
        return String.format("Order{id=%d, foodId=%d, userId=%d, foodName='%s', quantity=%d, price=%.2f}", 
                id, foodId, userId, foodName, quantity, getPrice());
    }
}