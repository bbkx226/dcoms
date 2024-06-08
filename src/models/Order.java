package models;

import java.io.Serializable;

public class Order implements Serializable {
    private final int foodId;
    private final String foodName;
    private int quantity;
    private double price;

    public Order(int foodId, String foodName, int quantity, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getFoodId() { return foodId; }
    public String getFoodName() { return foodName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int qty) { this.quantity = qty; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getTotalPrice() { return quantity * price; }

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