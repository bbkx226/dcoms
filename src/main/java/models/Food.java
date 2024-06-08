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
    public int getId() { return id; } // Returns the ID of the food item
    public String getName() { return name; } // Returns the name of the food item
    public void setName(String name) { this.name = name; } // Sets the name of the food item
    public int getQty() { return qty; } // Returns the quantity of the food item
    public void setQty(int qty) { this.qty = qty; } // Sets the quantity of the food item
    public double getPrice() { return price; } // Returns the price of the food item
    public void setPrice(double price) { this.price = price; } // Sets the price of the food item

    // Converts the food item to a string
    @Override
    public String toString() {
        return  id + "," +
                name + "," +
                qty + "," +
                price;
    }

    // Creates a food item from a string
    public static Food fromString(String foodString) {
        String[] parts = foodString.split(",");
        int id = Integer.parseInt(parts[0]);
        int qty = Integer.parseInt(parts[2]);
        double price = Double.parseDouble(parts[3]);
        return new Food(id, parts[1], qty, price);
    }
}