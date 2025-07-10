package com.ecomhub.cartservice.domain.port;

import com.ecomhub.cartservice.domain.entity.Cart;

public interface CartRepository {
    Cart findByUserId(String userId);
    void save( Cart cart);
}