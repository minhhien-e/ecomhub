package com.example.inventory.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;


@Document(collection = "inventories")
public class InventoryDocument {

    @Id
    private UUID inventoryId;

    @Indexed
    private UUID locationId;

    @Indexed
    private boolean active = true;

    private List<VariantItem> variants;


    public static class VariantItem {
        @Indexed
        private UUID variantId;
        private int quantity;
        private String variantName;

        public VariantItem(UUID variantId, int quantity, String variantName) {
            this.variantId = variantId;
            this.quantity = quantity;
            this.variantName = variantName;
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
        public String getVariantName() {
            return variantName;
        }
        public void setVariantName(String variantName) {
            this.variantName = variantName;
        }
        @Override
        public String toString() {
            return "VariantItem{" +
                    "variantId=" + variantId +
                    ", quantity=" + quantity +
                    ", variantName='" + variantName + '\'' +
                    '}';
        }
        public VariantItem() {}
        public VariantItem(UUID variantId, int quantity) {
            this.variantId = variantId;
            this.quantity = quantity;
        }
        public VariantItem(UUID variantId) {
            this.variantId = variantId;
        }
        public VariantItem(int quantity) {
            this.quantity = quantity;
        }
    }
    public void setVariants(List<VariantItem> variants) {
        if (variants != null && variants.size() > 15) {
            throw new IllegalArgumentException("Mỗi sản phẩm chỉ được tối đa 15 variants");
        }
        this.variants = variants;
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public List<VariantItem> getVariants() {
        return variants;
    }
}
