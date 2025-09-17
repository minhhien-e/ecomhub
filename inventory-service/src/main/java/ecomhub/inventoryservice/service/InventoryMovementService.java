package ecomhub.inventoryservice.service;

import ecomhub.inventoryservice.model.InventoryMovement;
import ecomhub.inventoryservice.repository.InventoryMovementRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
//@RequiredArgsConstructor
public class InventoryMovementService {

    private final InventoryMovementRepository repository;
    private final ProductServiceClient productServiceClient;

    // Constructor injection
    public InventoryMovementService(InventoryMovementRepository repository,
                                    ProductServiceClient productServiceClient) {
        this.repository = repository;
        this.productServiceClient = productServiceClient;
    }

    public InventoryMovement addMovement(InventoryMovement movement) {
        // 1. Lưu log vàu MongoDB
        InventoryMovement saved = repository.save(movement);

        // 2. Gọi sang product-service để update stock
        productServiceClient.updateStock(
                saved.getVariantId(),
                saved.getShopId(),
                saved.getQuantityChange()
        );

        return saved;
    }

    public List<InventoryMovement> getAllMovements() {
        return repository.findAll();
    }
}
