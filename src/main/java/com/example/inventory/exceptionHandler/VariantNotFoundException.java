package com.example.inventory.exceptionHandler;

public class VariantNotFoundException extends RuntimeException {
    public VariantNotFoundException(String variantId) {
        super("Variant not found with id: " + variantId);
    }
}