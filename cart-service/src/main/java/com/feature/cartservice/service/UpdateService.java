package com.feature.cartservice.service;

import com.feature.cartservice.dto.response.ApiResponse;
import com.feature.cartservice.exception.custom.*;
import com.feature.cartservice.model.Cart;
import com.feature.cartservice.model.CartItem;
import com.feature.cartservice.repository.CartItemRepository;
import com.feature.cartservice.repository.CartRepository;
import com.feature.cartservice.service.external.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static java.rmi.server.LogStream.log;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    @Transactional
    public Cart addToCart(UUID customerId, UUID variantId, int quantity, double price) {
        if (customerId == null) throw new MissingCustomerIdException();
        if (variantId == null) throw new MissingVariantIdException();
        if (quantity <= 0) throw new InvalidQuantityException(quantity);
        if (price < 0) throw new InvalidPriceException(price);

        Cart cart = cartRepository.findById(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setId(customerId);
                    newCart.setNote("New Cart");
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
        productServiceClient.getVariantDetails(variantId);

        Optional<CartItem> existingItemOpt = cartItemRepository.findCartItemByCartIdAndVariantId(cart.getId(), variantId);
        CartItem itemToSave;

        if (existingItemOpt.isEmpty()) {
            itemToSave = new CartItem();
            itemToSave.setId(UUID.randomUUID());
            itemToSave.setCart(cart);
            itemToSave.setVariantId(variantId);
            itemToSave.setQuantity(quantity);
            itemToSave.setPrice(price);
        } else {
            itemToSave = existingItemOpt.get();
            itemToSave.setQuantity(itemToSave.getQuantity() + quantity);
            itemToSave.setPrice(price);
        }
        cartItemRepository.save(itemToSave);

        return cart;
    }

    @Transactional
    public void updateCartItem(UUID cartId, UUID variantID, int quantity, double price) {
        if (cartId == null) throw new MissingCartIdException();
        if (variantID == null) throw new MissingVariantIdException();
        CartItem item = cartItemRepository.findCartItemByCartIdAndVariantId(cartId, variantID)
                .orElseThrow(() -> new CartItemNotFoundException(variantID));
        int finalQuantity = item.getQuantity() + quantity;

        if (finalQuantity < 0) {
            throw new InvalidQuantityException(quantity);
        }

        if (finalQuantity == 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(finalQuantity);
            item.setPrice(price);
            cartItemRepository.save(item);
        }
    }

}
