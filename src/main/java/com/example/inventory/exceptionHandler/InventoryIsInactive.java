package com.example.inventory.exceptionHandler;

public class InventoryIsInactive extends RuntimeException{
    public InventoryIsInactive(String inventoryId) {
        super("Inventory with id: " + inventoryId + " is inactive");
    }
}
