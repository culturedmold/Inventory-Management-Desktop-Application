package com.cth.inventoryManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int currentProductId = 0;
    private static int currentPartId = 0;

    public static int incrementProductId() {
        ++currentProductId;
        return currentProductId;
    }
    public static int incrementPartId() {
        ++currentPartId;
        return currentPartId;
    }
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
