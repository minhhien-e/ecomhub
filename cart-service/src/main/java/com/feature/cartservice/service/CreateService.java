package com.feature.cartservice.service;

import com.feature.cartservice.exception.custom.*;
import com.feature.cartservice.model.Cart;
import com.feature.cartservice.model.CartItem;
import com.feature.cartservice.repository.CartItemRepository;
import com.feature.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // Cart
    @Transactional
    public void createCart(UUID customerId) {
        if (customerId == null) {
            throw new MissingCustomerIdException();
        }
        cartRepository.findById(customerId);
    }


    //CartItem

//    @Transactional
//    public CartItem createCartItem(UUID cartId, UUID variantId, int quantity, double price) {
//        if (cartId == null) throw new MissingCartIdException();
//        if (variantId == null) throw new MissingVariantIdException();
//        if (quantity <= 0) throw new InvalidQuantityException(quantity);
//        if (price < 0) throw new InvalidPriceException(price);
//
//
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new CartItemNotFoundException(cartId));
//
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setVariantId(variantId);
//        cartItem.setQuantity(quantity);
//        cartItem.setPrice(price);
//        cartItem.setCreatedAt(LocalDateTime.now());
//        return cartItemRepository.save(cartItem);
//    }


}
