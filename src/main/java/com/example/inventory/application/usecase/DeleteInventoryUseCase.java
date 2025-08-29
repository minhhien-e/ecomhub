package com.example.inventory.application.usecase;

import java.util.UUID;

public interface DeleteInventoryUseCase {
    void delete(UUID inventoryId);
}
