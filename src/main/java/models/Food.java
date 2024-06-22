package models;

import java.io.Serializable;

// Represents a food item in the system
public class Food implements Serializable {
    private final int id;
    private String name;
    private int qty;
    private double price;

    // Constructor to initialize the food item
    public Food(int id, String name, int qty, double price) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Converts the food item to a string
    @Override
    public String toString() { return String.format("%d,%s,%d,%.2f", id, name, qty, price); }

    // Creates a food item from a string
    public static Food fromString(String foodString) {
        String[] parts = foodString.split(",");

        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int qty = Integer.parseInt(parts[2]);
        double price = Double.parseDouble(parts[3]);

        return new Food(id, name, qty, price);
    }
}