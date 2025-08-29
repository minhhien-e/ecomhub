package com.example.inventory.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;


public class InventoryRequest {

    @NotNull(message = "locationId không được null")
    private UUID locationId;
    @NotNull(message = "Danh sách variant không được null")
    @Size(max = 15, message = "Mỗi inventory chỉ được tối đa 15 variant")
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
    public InventoryRequest(UUID locationId, List<VariantRequest> variants) {
        this.locationId = locationId;
        this.variants = variants;
    }
    public InventoryRequest() {}
    @Override
    public String toString() {
        return "InventoryRequest{" +
                "locationId=" + locationId +
                ", variants=" + variants +
                '}';
    }
    public InventoryRequest(UUID locationId) {
        this.locationId = locationId;
    }
    public InventoryRequest(List<VariantRequest> variants) {
        this.variants = variants;
    }
    public InventoryRequest(UUID locationId, VariantRequest variantRequest) {
        this.locationId = locationId;
        this.variants = List.of(variantRequest);
    }
}
