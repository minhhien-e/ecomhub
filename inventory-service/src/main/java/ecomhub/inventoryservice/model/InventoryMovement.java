package ecomhub.inventoryservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "inventory_movement")

@Data
public class InventoryMovement {
    @Id
    private UUID movementId;
    private UUID variantId;
    private UUID shopId;
    private int quantityChange;
    private String type;
    private Instant createdAt = Instant.now();
}
