package com.ecomhub.cartservice.domain.port;

import com.ecomhub.cartservice.domain.entity.Cart;

public interface CartCache {
    Cart get(String userId);
    void set(String userId, Cart cart);
}