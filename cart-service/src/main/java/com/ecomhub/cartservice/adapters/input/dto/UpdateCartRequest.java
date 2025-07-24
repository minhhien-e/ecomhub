package com.ecomhub.cartservice.adapters.input.dto;

import com.ecomhub.cartservice.domain.entity.CartItem;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String productId;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @PositiveOrZero
    private double price;

    @Min(1)
    private int quantity;

    public CartItem toCartItem() {
        return new CartItem(productId, name, price, quantity, image);
    }
}
