package com.example.inventory.application.command;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


public class InventoryRequest {

    @NotNull(message = "locationId không được null")
    private UUID locationId;


    @Valid
    private List<VariantRequest> variants;
    public List<VariantRequest> getVariants() {
        return variants;
    }
    public void setVariants(List<VariantRequest> variants) {
        this.variants = variants;
    }
    public UUID getLocationId() {
        return locationId;
    }
    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

}
