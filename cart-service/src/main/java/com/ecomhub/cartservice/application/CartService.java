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
        Cart cart = getOrCreateCart(userId);
        cart.addItem(item);
        cartRepository.save(cart);
        cartCache.set(userId, cart);
    }

    @Transactional
    public void clearCart(String userId) {
        Cart cart = new Cart(userId);
        cartRepository.save(cart);
        cartCache.set(userId, cart);
    }

    @Transactional
    public void removeItem(String userId, String productId) {
        Cart cart = getOrCreateCart(userId);
        cart.removeItem(productId);
        cartRepository.save(cart);
        cartCache.set(userId, cart);
    }

    @Transactional
    public void updateItem(String userId, CartItem updatedItem) {
        Cart cart = getOrCreateCart(userId);
        cart.updateItem(updatedItem);
        cartRepository.save(cart);
        cartCache.set(userId, cart);
    }

    public Cart getCart(String userId) {
        Cart cart = cartCache.get(userId);
        if (cart != null) {
            return cart;
        }
        cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartCache.set(userId, cart);
        }
        return cart;
    }

    private Cart getOrCreateCart(String userId) {
        Cart cart = cartCache.get(userId);
        if (cart != null) {
            return cart;
        }

        cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            return cart;
        }

        return new Cart(userId);
    }
}
