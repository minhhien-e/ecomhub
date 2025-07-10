package com.ecomhub.cartservice.application;

import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.entity.CartItem;
import com.ecomhub.cartservice.domain.port.CartCache;
import com.ecomhub.cartservice.domain.port.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartCache cartCache;
    private final CartRepository cartRepository;

    public void addItemToCart(CartItem item, String userId) {
        Cart cart = cartCache.get(userId);
        if (cart == null) cart = cartRepository.findByUserId(userId);
        cart.addItem(item);
        cartCache.set(userId, cart);
        cartRepository.save(cart);
    }
}
