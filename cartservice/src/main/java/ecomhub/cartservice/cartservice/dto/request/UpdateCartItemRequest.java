package ecomhub.cartservice.cartservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;

public record UpdateCartItemRequest(


        @NotNull(message = "VariantId is required")
        UUID variantId,
        @NotNull(message = "Quantity is required")
        int quantity,


        @PositiveOrZero(message = "Price must be >= 0")
        double price
) {}
