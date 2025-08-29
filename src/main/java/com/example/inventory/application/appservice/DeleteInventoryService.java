package com.example.inventory.application.appservice;

import com.example.inventory.application.usecase.DeleteInventoryUseCase;
import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.repository.InventoryRepository;
import com.example.inventory.exceptionHandler.InventoryNotFoundException;


import org.springframework.stereotype.Service;


import java.util.UUID;

@Service

public class DeleteInventoryService implements DeleteInventoryUseCase {

    private final InventoryRepository inventoryRepository;


    public DeleteInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
//    private final LogService logService;

    @Override
    public void delete(UUID inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException(inventoryId.toString()));
        if(inventory != null){
            if(!inventory.isActive()  ){
                throw new RuntimeException("Inventory is inactive");
            }
            inventory.setActive(false);
            inventoryRepository.save(inventory);
        }

//        LogRequest logRequest = new LogRequest(
//                "DELETE_INVENTORY",
//                "Xóa inventory với id " + inventoryId,
//                "",
//                LocalDateTime.now()
//        );
//        logService.createLog(logRequest.toDomain());
    }
}

