package com.example.inventory.mapper;



import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.model.Variant;
import com.example.inventory.infrastructure.persistence.InventoryDocument;

import java.util.stream.Collectors;

public class InventoryMapper {

    // Domain -> Document
    public static InventoryDocument toDocument(Inventory inventory) {
        InventoryDocument doc = new InventoryDocument();
        doc.setInventoryId(inventory.getInventoryId());
        doc.setLocationId(inventory.getLocationId());
        doc.setActive(inventory.isActive());

        if (inventory.getVariants() != null) {
            doc.setVariants(
                    inventory.getVariants().stream()
                            .map(v -> {
                                InventoryDocument.VariantItem vi = new InventoryDocument.VariantItem();
                                vi.setVariantId(v.getVariantId());
                                vi.setQuantity(v.getQuantity());
                                return vi;
                            })
                            .collect(Collectors.toList())
            );
        }

        return doc;
    }


    // Document -> Domain
    public static Inventory toDomain(InventoryDocument doc) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(doc.getInventoryId());
        inventory.setLocationId(doc.getLocationId());
        inventory.setActive(doc.isActive());

        if (doc.getVariants() != null) {
            inventory.setVariants(
                    doc.getVariants().stream()
                            .map(v -> new Variant(v.getVariantId() ,v.getQuantity()))
                            .collect(Collectors.toList())
            );
        }

        return inventory;
    }

}
