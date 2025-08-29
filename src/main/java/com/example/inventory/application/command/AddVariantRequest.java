package com.example.inventory.application.command;

import lombok.Data;


import java.util.UUID;


public class AddVariantRequest {
    private UUID inventoryId;
    private UUID variantId;
    private int quantity;
    private String note;

    public AddVariantRequest(UUID inventoryId, UUID variantId, int quantity, String note) {
        this.inventoryId = inventoryId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.note = note;
    }
    public AddVariantRequest() {}
    public UUID getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }
    public UUID getVariantId() {
        return variantId;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    @Override
    public String toString() {
        return "AddVariantRequest{" +
                "inventoryId=" + inventoryId +
                ", variantId=" + variantId +
                ", quantity=" + quantity +
                ", note='" + note + '\'' +
                '}';
    }
    public AddVariantRequest(UUID inventoryId, UUID variantId, int quantity) {
        this.inventoryId = inventoryId;
        this.variantId = variantId;
        this.quantity = quantity;
    }
    public AddVariantRequest(UUID inventoryId, UUID variantId) {
        this.inventoryId = inventoryId;
        this.variantId = variantId;
    }
    public AddVariantRequest(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }
    public AddVariantRequest(int quantity) {
        this.quantity = quantity;
    }
    public AddVariantRequest(UUID inventoryId, int quantity) {
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.variantId = UUID.randomUUID();
        this.note = "";
    }
    public AddVariantRequest(UUID inventoryId, int quantity, String note) {
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.variantId = UUID.randomUUID();
        this.note = note;
        if (note == null) {
            this.note = "";
        }
        if (note.isBlank()) {
            this.note = "";
        }
        if (note.isEmpty()) {
            this.note = "";
        }
    }

}
