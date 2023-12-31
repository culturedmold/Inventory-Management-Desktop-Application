package com.cth.inventoryManagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Each instance of the product class is unique. This class does not contain static data.
 * @author Tyler Hampton (Cory)
 */
public class Product {
    /**
     * List of all parts associated with a product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Product ID must be unique and is generated by Inventory.incrementProductId()
     */
    private int id;
    /**
     * Product name
     */
    private String name;
    /**
     * Price of one product instance
     */
    private double price;
    /**
     * Current stock level of the product instance
     */
    private int stock;
    /**
     * Minimum acceptable stock level of the product
     */
    private int min;
    /**
     * Maximum acceptable stock level of the product
     */
    private int max;

    /**
     * Constructs a new product to save in inventory
     * @param id - unique product ID must be generated from Inventory.incrementProductId()
     * @param name - product name
     * @param price - price of a single instance of the product
     * @param stock - current stock of product
     * @param min - minimum acceptable stock level of the product
     * @param max - maximum acceptable stock level of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Set the product ID
     * @param id - must generate unique ID using Inventory.incrementProductId()
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the product name
     * @param name - name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the price of the product
     * @param price - price of one instance of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set the current stock/inventory level of the product
     * @param stock - current stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Set minimum acceptable stock level of the product
     * @param min - min stock level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Set maximum acceptable stock level of the product
     * @param max - max must be greater than min and greater than stock
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get product ID
     * @return - return product ID as int
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get product name
     * @return - returns name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Get product price
     * @return - returns price as double
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Get product current stock level
     * @return - returns stock as int
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Get min acceptable stock level
     * @return - returns min as int
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Get max acceptable stock level
     * @return - returns max as int
     */
    public int getMax() {
        return this.max;
    }

    /**
     * Get all associated products
     * @return - returns all associated products as ObservableList
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    /**
     * Add a product to associatedParts
     * @param part - part to add
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Remove a part from associatedParts
     * @param part - part to remove
     * @return - returns boolean: true if removed, false otherwise
     */
    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }
}
