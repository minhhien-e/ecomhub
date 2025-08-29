package com.example.inventory.infrastructure.persistence;

import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.model.Variant;
import com.example.inventory.domain.repository.InventoryRepository;
import com.example.inventory.mapper.InventoryMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final MongoTemplate mongoTemplate;

    public InventoryRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Inventory> findById(UUID inventoryId) {
        InventoryDocument doc = mongoTemplate.findById(inventoryId, InventoryDocument.class);
        return Optional.ofNullable(doc != null ? InventoryMapper.toDomain(doc) : null);
    }

    @Override
    public void save(Inventory inventory) {
        InventoryDocument doc = mapToDocument(inventory);
        mongoTemplate.save(doc);
    }

    @Override
    public void delete(UUID inventoryId) {
        InventoryDocument doc = mongoTemplate.findById(inventoryId, InventoryDocument.class);
        if (doc != null) {
            mongoTemplate.remove(doc);
        } else {
            throw new RuntimeException("Inventory not found");
        }
    }

    private Inventory mapToDomain(InventoryDocument doc) {
        var variants = doc.getVariants().stream()
                .map(v -> new Variant(v.getVariantId(), v.getQuantity()))
                .toList();
        return new Inventory(doc.getInventoryId(), variants, doc.getLocationId());
    }

    private InventoryDocument mapToDocument(Inventory inventory) {
        InventoryDocument doc = new InventoryDocument();
        doc.setInventoryId(inventory.getInventoryId());
        doc.setLocationId(inventory.getLocationId());
        doc.setActive(inventory.isActive());
        var variantItems = inventory.getVariants().stream()
                .map(v -> {
                    InventoryDocument.VariantItem vi = new InventoryDocument.VariantItem();
                    vi.setVariantId(v.getVariantId());
                    vi.setQuantity(v.getQuantity());
                    return vi;
                }).toList();
        doc.setVariants(variantItems);
        return doc;
    }
}
