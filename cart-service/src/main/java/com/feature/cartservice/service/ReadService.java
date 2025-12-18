package com.feature.cartservice.service;

import com.feature.cartservice.dto.response.ApiResponse;
import com.feature.cartservice.exception.custom.CartItemNotFoundException;
import com.feature.cartservice.exception.custom.CartNotFoundException;
import com.feature.cartservice.model.Cart;
import com.feature.cartservice.model.CartItem;
import com.feature.cartservice.repository.CartItemRepository;
import com.feature.cartservice.repository.CartRepository;
import com.feature.cartservice.service.external.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReadService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    //Cart
    public Cart getCart(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseGet(() -> {
                    log.info("User {} chưa có giỏ hàng, đang tạo mới...",cartId );
                    Cart newCart = new Cart();
                    newCart.setId(cartId);
                    newCart.setNote("Giỏ hàng mới");
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    public CartItem getProductInfo(UUID variantId) {

        ApiResponse<CartItem> response= productServiceClient.getVariantDetails(variantId);
        if(response.errorCode()!=0 || response.statusCode() !=200){
            throw new CartItemNotFoundException(variantId);
        }

        return cartItemRepository.findById(variantId)
                .orElse(response.data());
    }



}
