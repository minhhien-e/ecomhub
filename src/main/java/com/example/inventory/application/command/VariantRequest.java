package com.example.inventory.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public class VariantRequest {

    @NotNull(message = "variantId không được null")
    private UUID variantId;
    private int quantity;
    private String note;

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

    public VariantRequest(UUID variantId, int quantity, String note) {
        this.variantId = variantId;
        this.quantity = quantity;
    }

    public VariantRequest() {
    }

    @Override
    public String toString() {
        return "VariantRequest{" +
                "variantId=" + variantId +
                ", quantity=" + quantity +
                ", note='" + note + '\'' +
                '}';
    }

    public VariantRequest(UUID variantId) {
        this.variantId = variantId;
    }

    public VariantRequest(int quantity) {
        this.quantity = quantity;
    }

    public VariantRequest(UUID variantId, int quantity) {
        this.variantId = variantId;
    }

    public VariantRequest(int quantity, String note) {
        this.quantity = quantity;
        this.note = note;
    }

}
