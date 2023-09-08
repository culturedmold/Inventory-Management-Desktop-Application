package com.cth.inventoryManagment;

/**
 * This class is for InHouse Parts that are stored in inventory. The class extends functionality of abstract class Part.
 *
 * @author Tyler Hampton (Cory)
 */
public class InHouse extends Part {
    /**
     * Machine ID for the in house part
     */
    private int machineId;

    /**
     * Constructor method for an in house part
     * @param id - the part ID needs to be unique and we should ensure that we always call Inventory.incrementPartId() when initializing
     * @param name - the part's name
     * @param stock - current stock/inventory level of the part
     * @param price - price of one unit
     * @param min - minimum acceptable stock level
     * @param max - maximum acceptable stock level
     * @param machineId - machine ID for the in house part
     */
    public InHouse(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Method we call when we want to set a machine ID
     * @param machineId - integer to set as the machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Method to return the machine ID
     * @return - returns machine ID as integer
     */
    public int getMachineID() {
        return this.machineId;
    }
}
