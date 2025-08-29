package com.example.inventory.application.appservice;

import com.example.inventory.application.command.UpdateInventoryRequest;
import com.example.inventory.application.usecase.UpdateInventoryUseCase;
import com.example.inventory.domain.repository.InventoryRepository;
import com.example.inventory.exceptionHandler.InventoryNotFoundException;
import com.example.inventory.exceptionHandler.VariantNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateInventoryService implements UpdateInventoryUseCase {

    private final InventoryRepository inventoryRepository;

    public UpdateInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void update(UpdateInventoryRequest request) {
        var inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> {
//                    logService.createLog(
//                            new LogRequest(
//                                    "UPDATE_INVENTORY_FAILED",
//                                    "Inventory not found",
//                                    "inventoryId: " + request.getInventoryId() + ", quantityChange: " + request.getQuantityChange(),
//                                    LocalDateTime.now()
//                            ).toDomain()
//                    );
                    return new InventoryNotFoundException(request.getInventoryId().toString());
                });
        if (!inventory.isActive()) {
            throw new RuntimeException("Inventory is inactive");
        }
        var variantQuantity = inventory.getVariants().stream()
                .filter(v -> v.getVariantId().equals(request.getVariantId()))
                .findFirst()
                .map(v -> v.getQuantity() + request.getQuantityChange())
                .orElseThrow(() -> {
                    // Ghi log trước khi ném exception
//                    logService.createLog(
//                            new LogRequest(
//                                    "UPDATE_INVENTORY_FAILED",
//                                    "Variant not found",
//                                    "inventoryId: " + request.getInventoryId() + ", variantId: " + request.getVariantId(),
//                                    LocalDateTime.now()
//                            ).toDomain()
//                    );
                    return new VariantNotFoundException(request.getVariantId().toString());
                });


        inventory.updateVariant(request.getVariantId(), variantQuantity);
        inventory.setLocationId(request.getLocationId());
        inventory.setActive(request.isActive());

        inventoryRepository.save(inventory);

//        logService.createLog(
//                new LogRequest(
//                        "UPDATE_INVENTORY_SUCCESS",
//                        "Cập nhật inventory thành công",
//                        "inventoryId: " + inventory.getInventoryId() + "\n variantId: " + request.getVariantId() + "\n quantity change: " + request.getQuantityChange()+"\n locationId:"+request.getLocationId()+"\n isActive:"+request.isActive()+"\n quantity now:"+variantQuantity,
//                        LocalDateTime.now()
//                ).toDomain()
//        );
    }
}
