package com.ecomhub.cartservice.application.service;

import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.entity.CartItem;
import com.ecomhub.cartservice.domain.exception.CartNotFoundException;
import com.ecomhub.cartservice.domain.exception.ItemNotFoundException;
import com.ecomhub.cartservice.domain.port.CartCache;
import com.ecomhub.cartservice.domain.port.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartCache cartCache;
    private final CartRepository cartRepository;

    @Transactional
    public void addItemToCart(CartItem item, String userId) {
        Cart cart = getOrCreateCart(userId);
        cart.addItem(item);
        persistCart(userId, cart);
    }

    @Transactional
    public void clearCart(String userId) {
        Cart cart = getCartOrThrow(userId);
        cart.clear();
        persistCart(userId, cart);
    }

    @Transactional
    public void removeItem(String userId, String productId) {
        Cart cart = getCartOrThrow(userId);
        boolean removed = cart.removeItem(productId);
        if (!removed) {
            throw new ItemNotFoundException("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
        persistCart(userId, cart);
    }

    @Transactional
    public void updateItem(String userId, CartItem updatedItem) {
        Cart cart = getCartOrThrow(userId);
        boolean updated = cart.updateItem(updatedItem);
        if (!updated) {
            throw new ItemNotFoundException("Không thể cập nhật vì sản phẩm không tồn tại trong giỏ hàng.");
        }
        persistCart(userId, cart);
    }

    public Cart getCart(String userId) {
        Cart cart = cartCache.get(userId);
        if (cart != null) return cart;

        cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            try {
                cartCache.set(userId, cart);
            } catch (Exception e) {
                log.warn("Không thể ghi giỏ hàng vào Redis khi load từ MongoDB (userId = {}).", userId, e);
            }
        }

        return cart;
    }

    private Cart getOrCreateCart(String userId) {
        Cart cart = getCart(userId);
        return (cart != null) ? cart : new Cart(userId);
    }

    private Cart getCartOrThrow(String userId) {
        Cart cart = getCart(userId);
        if (cart == null) {
            throw new CartNotFoundException("Giỏ hàng không tồn tại.");
        }
        return cart;
    }

    private void persistCart(String userId, Cart cart) {
        cartRepository.save(cart);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() { // Xem lại hàm , anotation
                try {
                    cartCache.set(userId, cart);
                    log.debug("Giỏ hàng đã được ghi vào Redis (userId = {})", userId);
                } catch (Exception e) {
                    log.error("Không thể ghi giỏ hàng vào Redis sau khi commit (userId = {}).", userId, e);
                }
            }
        });
    }
}
