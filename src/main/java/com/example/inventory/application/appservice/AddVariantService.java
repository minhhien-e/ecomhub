package com.example.inventory.application.appservice;

import com.example.inventory.application.command.AddVariantRequest;
import com.example.inventory.application.usecase.AddVariantUseCase;
import com.example.inventory.domain.model.Variant;
import com.example.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddVariantService implements AddVariantUseCase {

    private final InventoryRepository inventoryRepository;
    public AddVariantService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    public void addVariant(AddVariantRequest request) {
        var inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if(!inventory.isActive()  ){
            throw new RuntimeException("Inventory is inactive");
        }

        inventory.addVariant(new Variant(request.getVariantId(), request.getQuantity()));
        inventoryRepository.save(inventory);

//        LogRequest logRequest = new LogRequest(
//                "ADD_VARIANT",
//                "Thêm variant vào inventory " + request.getInventoryId(),
//                request.getNote(),
//                LocalDateTime.now()
//        );
//        logService.createLog(logRequest.toDomain());
    }


}
