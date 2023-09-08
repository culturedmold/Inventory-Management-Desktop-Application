package com.cth.inventoryManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class contains all parts and products in inventory, as well as methods to get parts, get products, update/delete parts or products, and search parts or products
 * This class provides static data that persists through our application.
 * FUTURE ENHANCEMENT: Storing inventory data in a database would allow the application to have actual utility. By only storing the data in memory, we have to add the inventory manually each time we open the application.
 *
 * @author Tyler Hampton (Cory)
 */
public class Inventory {
    /**
     * An ObservableList of parts in inventory
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * An ObservableList of products in inventory
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Holds the current productId so we can generate a unique ID for a new product in inventory that is incremented one higher than the current ID
     */
    private static int currentProductId = 0;

    /**
     * Holds the current partId so we can generate a unique ID for a new part in inventory incremented one higher than the current ID
     */
    private static int currentPartId = 0;

    /**
     * Method to increment and return a productId
     * @return - returns a productId that is one higher than currentProductId
     */
    public static int incrementProductId() {
        ++currentProductId;
        return currentProductId;
    }

    /**
     * Method to increment and return a partId
     * @return - returns a partId that is one higher than currentPartId
     */
    public static int incrementPartId() {
        ++currentPartId;
        return currentPartId;
    }

    /**
     * Method to add a part to allParts in Inventory
     * @param newPart - takes a new Part as parameter/argument
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Method to add a new product allProducts in Inventory
     * @param newProduct - takes a new Product as parameter/argument
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    // Lookup parts by ID

    // Lookup products by ID

    // Lookup parts by name

    // Lookup products by name

    /**
     * Method to update a product at a specified index
     * @param modifiedProduct - the product we are sending to update at the specific index
     * @param index - index of allProducts in Inventory that we are updating
     */
    public static void updateProduct(Product modifiedProduct, int index) {
        Inventory.getAllProducts().set(index, modifiedProduct);
    }

    /**
     * Method to update part
     * This method will be used in our ModifyPartController to update a part with new/altered properties
     *
     * LOGIC ERROR: Initially when modifying parts/products, I was seeing changes reflect even if the user hadn't hit "Save" on the form. This is because the data is static data stored in Inventory.
     * To solve this, I created temporary variables in my controllers to house the form data that would only be passed into the relevant update/delete/add methods when appropriate conditions were met.
     *
     * @param index - index of allParts in Inventory that we are updating
     * @param modifiedPart - the modified part to go into the specified index
     */
    public static void updatePart(int index, Part modifiedPart) {
        Inventory.getAllParts().set(index, modifiedPart);
    }

    /**
     * Method to delete a part from inventory
     * @param selectedPart - our part to be deleted
     * @return - returns boolean: true if deleted, otherwise false
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Method to delete a product from inventory
     * @param selectedProduct - our product to be deleted
     * @return - returns boolean: true if deleted, otherwise false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Method to get all parts from allPArts list in inventory
     * @return - returns allParts as ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Method to get all products from inventory
     * @return - returns all products as ObservableList
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
