package com.example.inventory.application.appservice;

import com.example.inventory.application.usecase.CreateInventoryUseCase;
import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.model.Variant;
import com.example.inventory.domain.repository.InventoryRepository;
import com.example.inventory.infrastructure.dto.InventoryRequest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateInventoryService implements CreateInventoryUseCase {

    private final InventoryRepository inventoryRepository;
    public CreateInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public UUID create(InventoryRequest request) {
        List<Variant> variants = request.getVariants() != null
                ? request.getVariants().stream()
                .map(v -> new Variant(v.getVariantId(), v.getQuantity()))
                .collect(Collectors.toList())
                : List.of();

        Inventory inventory = new Inventory(variants, request.getLocationId());
        inventoryRepository.save(inventory);

//        LogRequest logRequest = new LogRequest(
//                "CREATE_INVENTORY",
//                "Tạo inventory tại location " + request.getLocationId(),
//                "Số variant: " + variants.size(),
//                LocalDateTime.now()
//        );
//        logService.createLog(logRequest.toDomain());
        return inventory.getInventoryId();
    }
}
