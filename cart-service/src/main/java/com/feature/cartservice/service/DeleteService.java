package com.feature.cartservice.service;

import com.feature.cartservice.dto.response.ApiResponse;
import com.feature.cartservice.exception.custom.CartItemNotFoundException;
import com.feature.cartservice.exception.custom.CartNotFoundException;
import com.feature.cartservice.model.Cart;
import com.feature.cartservice.model.CartItem;
import com.feature.cartservice.repository.CartItemRepository;
import com.feature.cartservice.repository.CartRepository;
import com.feature.cartservice.service.external.ProductServiceClient;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    @Transactional
    public void removeItemFromCart(UUID cartId, UUID variantId) {
        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndVariantId(cartId, variantId)
                .orElseThrow(() -> new CartItemNotFoundException(variantId));
        cartItemRepository.delete(cartItem);
    }



    public void removeCart(UUID customerId) {
        cartRepository.deleteById(customerId);
    }

    public void clearCart(@NotNull(message = "CartId is required") UUID uuid) {
        cartItemRepository.removeCartItemByCart_Id((uuid));
    }

    public void deleteCart(@NotNull(message = "Cart id is required") UUID uuid) {
        cartRepository.removeCartById(uuid);
    }
}
