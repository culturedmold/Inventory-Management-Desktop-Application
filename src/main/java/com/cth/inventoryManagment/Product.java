package com.cth.inventoryManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    // Constructor
    Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }

    // Getters
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public int getStock() {
        return this.stock;
    }
    public int getMin() {
        return this.min;
    }
    public int getMax() {
        return this.max;
    }
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    // Add part
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    // Delete part
    public boolean deleteAssociatedPart(Part part) {
        this.associatedParts.remove(part);
        return true;
    }
}
