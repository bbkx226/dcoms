package models;

import java.io.Serializable;

public class Food implements Serializable {
    private final int id;
    private String name;
    private int qty;
    private double price;

    public Food(int id, String name, int qty, double price) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return  id + "," +
                name + "," +
                qty + "," +
                price;
    }

    public static Food fromString(String foodString) {
        String[] parts = foodString.split(",");
        int id = Integer.parseInt(parts[0]);
        int qty = Integer.parseInt(parts[2]);
        double price = Double.parseDouble(parts[3]);
        return new Food(id, parts[1], qty, price);
    }
}
