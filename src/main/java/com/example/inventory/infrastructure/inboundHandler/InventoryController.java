package com.example.inventory.infrastructure.inboundHandler;


import com.example.inventory.application.command.AddVariantRequest;
import com.example.inventory.application.command.UpdateInventoryRequest;
import com.example.inventory.application.command.VariantRequest;
import com.example.inventory.application.usecase.AddVariantUseCase;
import com.example.inventory.application.usecase.CreateInventoryUseCase;
import com.example.inventory.application.usecase.DeleteInventoryUseCase;
import com.example.inventory.application.usecase.UpdateInventoryUseCase;
import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.repository.InventoryRepository;
import com.example.inventory.infrastructure.dto.InventoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;


@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final UpdateInventoryUseCase updateInventoryUseCase;
    private final CreateInventoryUseCase createInventoryUseCase;
    private final AddVariantUseCase addVariantUseCase;
    private final InventoryRepository inventoryRepository;
    private final DeleteInventoryUseCase deleteInventoryUseCase;
    public InventoryController(UpdateInventoryUseCase updateInventoryUseCase, CreateInventoryUseCase createInventoryUseCase, AddVariantUseCase addVariantUseCase, InventoryRepository inventoryRepository, DeleteInventoryUseCase deleteInventoryUseCase) {
        this.updateInventoryUseCase = updateInventoryUseCase;
        this.createInventoryUseCase = createInventoryUseCase;
        this.addVariantUseCase = addVariantUseCase;
        this.inventoryRepository = inventoryRepository;
        this.deleteInventoryUseCase = deleteInventoryUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable("id") UUID inventoryId) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return ResponseEntity.ok(inventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") UUID inventoryId) {
        deleteInventoryUseCase.delete(inventoryId);
        return ResponseEntity.ok("Inventory deleted successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody @Valid UpdateInventoryRequest request) {

        updateInventoryUseCase.update(request);
        return ResponseEntity.ok("Inventory updated successfully");
    }

    @PostMapping("/with-variants")
    public ResponseEntity<UUID> createInventoryWithVariants(@Valid @RequestBody InventoryRequest request) {
        UUID inventoryId = createInventoryUseCase.create(request);
        return ResponseEntity.ok(inventoryId);
    }

    @PostMapping("/{id}/variants")
    public ResponseEntity<String> addVariant(
            @PathVariable("id") UUID inventoryId,
            @RequestBody VariantRequest request) {

        AddVariantRequest addRequest = new AddVariantRequest(
                inventoryId,
                request.getVariantId(),
                request.getQuantity(),
                request.getNote()
        );

        addVariantUseCase.addVariant(addRequest);
        return ResponseEntity.ok("Variant added successfully");
    }
}
