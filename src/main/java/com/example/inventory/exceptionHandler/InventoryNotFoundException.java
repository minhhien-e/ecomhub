package com.example.inventory.exceptionHandler;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String inventoryId) {
        super("Inventory not found with id: " + inventoryId);

    }
}