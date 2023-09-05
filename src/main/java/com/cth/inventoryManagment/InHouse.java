package com.cth.inventoryManagment;

public class InHouse extends Part {
    static private int machineId;

    // Constructor
    public InHouse(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    public int getMachineID() {
        return this.machineId;
    }
}
