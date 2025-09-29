package ecomhub.inventoryservice.repository;

import ecomhub.inventoryservice.model.InventoryMovement;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface InventoryMovementRepository extends MongoRepository<InventoryMovement, UUID> {
List<InventoryMovement> findByVariantId(UUID variantId);
List<InventoryMovement> findByVariantIdAndShopIdOrderByCreatedAtDesc  (UUID variantId, UUID shopId);
List<InventoryMovement> findByVariantIdAndShopIdAndCreatedAtBetween  (UUID variantId, UUID shopId, LocalDateTime from, LocalDateTime to);
}
