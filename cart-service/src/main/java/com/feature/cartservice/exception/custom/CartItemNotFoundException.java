package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

import java.util.UUID;

public class CartItemNotFoundException extends HttpException{
    public CartItemNotFoundException(UUID CartItem) {
        super(ErrorCode.CART_ITEM_NOT_FOUND, "Cart item not found with id: " + CartItem);
    }
}
