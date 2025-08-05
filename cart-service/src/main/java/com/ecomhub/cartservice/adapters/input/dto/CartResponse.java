package com.ecomhub.cartservice.adapters.input.dto;

import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.entity.CartItem;
import java.util.List;

public class CartResponse {
    public String userId;
    public List<CartItem> items;

    public static CartResponse from(Cart cart) {
        CartResponse r = new CartResponse();
        r.userId = cart != null ? cart.getUserId() : null;
        r.items = cart != null ? cart.getItems() : List.of();
        return r;
    }
}
