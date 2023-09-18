package com.cth.inventoryManagement.model;

/**
 * This class is for Outsourced Parts that are stored in inventory. The class extends functionality of abstract class Part.
 * @author Tyler Hampton (Cory)
 */
public class Outsourced extends Part {
    /**
     * The company name of the outsourced part
     */
    static String companyName;

    /**
     * Constructor for a new outsourced part
     * @param id - unique ID must come from Inventory.incrementPartId()
     * @param name - name of part
     * @param price - price of one unit
     * @param stock - current stock level
     * @param min - minimum acceptable stock level
     * @param max - maximum acceptable stock level
     * @param companyName - company name of outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Set the company name
     * @param companyName - company name we are setting
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get the company name
     * @return - returns company name as String
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
