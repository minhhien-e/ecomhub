package com.example.inventory.domain.model;

import lombok.*;

import java.util.UUID;



public class Variant {
    private UUID variantId;
    private int quantity;

    public Variant(UUID variantId, int quantity) {
        this.quantity =quantity;
        this.variantId =  variantId;
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
}
