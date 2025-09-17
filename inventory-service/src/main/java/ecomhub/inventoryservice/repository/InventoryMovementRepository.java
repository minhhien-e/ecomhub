package ecomhub.inventoryservice.repository;

import ecomhub.inventoryservice.model.InventoryMovement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryMovementRepository extends MongoRepository<InventoryMovement, String> {
List<InventoryMovement> findByVariantId(UUID variantId);
}
