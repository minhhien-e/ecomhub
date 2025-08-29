package com.example.inventory.application.usecase;

import java.util.UUID;

import com.example.inventory.infrastructure.dto.InventoryRequest;

public interface CreateInventoryUseCase {
    UUID create(InventoryRequest request);

}
