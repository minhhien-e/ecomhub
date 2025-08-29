package com.example.inventory.domain.repository;


import com.example.inventory.domain.model.Inventory;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository {
    Optional<Inventory> findById(UUID inventoryId);
    void save(Inventory inventory);
    void delete(UUID inventoryId);
}
