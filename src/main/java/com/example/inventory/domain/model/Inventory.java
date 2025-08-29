package com.example.inventory.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



public class Inventory {
    private UUID inventoryId;
    private List<Variant> variants;
    private UUID locationId;
    private boolean active;

    public Inventory() {}

    public Inventory(UUID inventoryId, List<Variant> variants, UUID locationId) {
        this.inventoryId = inventoryId;
        this.variants = variants;
        this.locationId = locationId;
        this.active = !variants.isEmpty() && variants.stream().anyMatch(v -> v.getQuantity() > 0);
    }

    public Inventory(List<Variant> variants, UUID locationId) {
        this(UUID.randomUUID(), variants, locationId);
    }

    public void addVariant(Variant variant) {
        variants.add(variant);
        updateActiveStatus();
    }


    public void updateVariant(UUID variantId, int newQuantity) {
        Optional<Variant> existing = variants.stream()
                .filter(v -> v.getVariantId().equals(variantId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(newQuantity);
        } else {
            throw new IllegalArgumentException("Variant ID không tồn tại trong inventory");
        }
        updateActiveStatus();
    }

    public void removeVariant(UUID variantId) {
        variants.removeIf(v -> v.getVariantId().equals(variantId));
        updateActiveStatus();
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private void updateActiveStatus() {
        this.active = variants.stream().anyMatch(v -> v.getQuantity() > 0);
    }
}
