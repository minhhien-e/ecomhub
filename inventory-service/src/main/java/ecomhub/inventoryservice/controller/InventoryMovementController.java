package ecomhub.inventoryservice.controller;


import ecomhub.inventoryservice.model.InventoryMovement;
import ecomhub.inventoryservice.service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory-movements")
public class InventoryMovementController {
    private final InventoryMovementService service;

    public InventoryMovementController(InventoryMovementService service) {
        this.service = service;
    }

    @PostMapping
    public InventoryMovement createMovement(@RequestBody InventoryMovement movement) {
        return service.addMovement(movement);   // Gọi service để tạo movement
    }

    @GetMapping
    public List<InventoryMovement> getAllMovements() {
        return service.getAllMovements();       // Lấy danh sách tất cả movement
    }
}
