package ecomhub.inventoryservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StockUpdateRequest {
    public UUID shopId;
    private int quantityChange;
}
