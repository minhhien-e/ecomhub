package ecomhub.inventoryservice.controller;


import ecomhub.inventoryservice.dto.CreateMovementRequest;
import ecomhub.inventoryservice.dto.MovementResponse;
import ecomhub.inventoryservice.model.InventoryMovement;
import ecomhub.inventoryservice.service.InventoryMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class InventoryMovementController {
    private final InventoryMovementService service;


    public InventoryMovementController(InventoryMovementService service){
        this.service = service;
    }

//    Tạo cái movement mới
    @PostMapping("/movements")
    public ResponseEntity<?> createMovement(@RequestBody CreateMovementRequest request){
        InventoryMovement saved = service.createMovement(request);
        MovementResponse response = new MovementResponse(saved.getId(), saved.getVariantId(), saved.getShopId(), saved.getQuantityChange(), saved.getType(), saved.getCreatedAt());
        return ResponseEntity.ok(response);
    }

//    Lấy lịch sử
    @GetMapping("/movements")
    public ResponseEntity<List<MovementResponse>> getMovements(@RequestParam UUID variantId, @RequestParam UUID shopId){
        List<InventoryMovement> list = service.getMovement(variantId, shopId);
        List<MovementResponse> response = list.stream().map(m -> new MovementResponse( m.getId(), m.getVariantId(), m.getShopId(), m.getQuantityChange(), m.getType(), m.getCreatedAt())).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
