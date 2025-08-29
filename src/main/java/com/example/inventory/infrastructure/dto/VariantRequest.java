package com.example.inventory.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VariantRequest {

    @NotNull(message = "variantId không được null")
    private UUID variantId;
    private int quantity;
    private String note;

    public String getNote() {
        return note != null ? note : "";
    }
    public int getQuantity() {
        return quantity;
    }
    public UUID getVariantId() {
        return variantId;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }

}
