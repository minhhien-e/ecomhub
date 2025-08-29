package com.example.inventory.application.command;

import lombok.Data;
import java.util.UUID;


public class UpdateInventoryRequest {
    private UUID inventoryId;
    private int QuantityChange;
    private UUID variantId;
    private UUID locationId;
    private String reason;
    private String description;
    private boolean active;

    public UpdateInventoryRequest(UUID inventoryId, int QuantityChange, UUID variantId, UUID locationId, String reason, String description, boolean active) {
        this.inventoryId = inventoryId;
        this.QuantityChange = QuantityChange;
        this.variantId = variantId;
        this.locationId = locationId;
        this.reason = reason;
        this.description = description;
        this.active = active;
    }
    public UpdateInventoryRequest() {}


    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantityChange() {
        return QuantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        QuantityChange = quantityChange;
    }

    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
