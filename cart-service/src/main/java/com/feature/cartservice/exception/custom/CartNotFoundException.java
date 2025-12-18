package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

import java.util.UUID;

public class CartNotFoundException extends HttpException{
    public CartNotFoundException(UUID cartId) {
        super(ErrorCode.CART_NOT_FOUND, "Cart not found with id: " + cartId);
    }
}
