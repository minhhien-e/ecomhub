package ecomhub.inventoryservice.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "inventory_movement")
@Data
public class InventoryMovement {
    @Id
    private UUID id;
    private UUID variantId;
    private UUID shopId;
    private int quantityChange;
    private String type;
    @CreatedDate
    private LocalDateTime createdAt;

    public InventoryMovement(UUID variantId, UUID shopId, int quantityChange, String type) {
        this.variantId = variantId;
        this.shopId = shopId;
        this.quantityChange = quantityChange;
        this.type = type;
    }
}
