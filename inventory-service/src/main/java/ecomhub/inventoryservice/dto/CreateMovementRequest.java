package ecomhub.inventoryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateMovementRequest {
    private UUID variantId;
    private UUID shopId;
    private int quantityChange;
    private String type;
    private LocalDateTime createAt;
}
