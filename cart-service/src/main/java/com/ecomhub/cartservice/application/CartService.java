package com.ecomhub.cartservice.application;

import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.entity.CartItem;
import com.ecomhub.cartservice.domain.port.CartCache;
import com.ecomhub.cartservice.domain.port.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartCache cartCache;
    private final CartRepository cartRepository;

    @Transactional
    public void addItemToCart(CartItem item, String userId) {
        Cart cart = Optional.ofNullable(cartCache.get(userId))
                .orElseGet(() -> cartRepository.findByUserId(userId));
        cart.addItem(item);
        cartCache.set(userId, cart);
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(String userId) {
        Cart cart = new Cart(userId);
        cartCache.set(userId, cart);
        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(String userId, String productId) {
        Cart cart = Optional.ofNullable(cartCache.get(userId))
                .orElseGet(() -> cartRepository.findByUserId(userId));

        cart.removeItem(productId);
        cartCache.set(userId, cart);
        cartRepository.save(cart);
    }

    @Transactional
    public void updateItem(String userId, CartItem updatedItem) {
        Cart cart = Optional.ofNullable(cartCache.get(userId))
                .orElseGet(() -> cartRepository.findByUserId(userId));

        cart.updateItem(updatedItem);
        cartCache.set(userId, cart);
        cartRepository.save(cart);
    }

}
