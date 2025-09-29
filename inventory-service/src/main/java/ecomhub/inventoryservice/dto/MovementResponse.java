package ecomhub.inventoryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MovementResponse {
    private UUID movementId;
    private UUID shopId;
    private UUID variantId;
    private int quantityChange;
    private String type;
    private LocalDateTime createdAt;

    public MovementResponse(UUID movementId, UUID variantId, UUID shopId, int quantityChange, String type, LocalDateTime createdAt) {
        this.movementId = movementId;
        this.variantId = variantId;
        this.shopId = shopId;
        this.quantityChange = quantityChange;
        this.type = type;
        this.createdAt = createdAt;
    }
}
