package ecomhub.cart.exception.custom;

import ecomhub.cart.enums.ErrorCode;

import java.util.UUID;

public class CartItemNotFoundException extends HttpException{
    public CartItemNotFoundException(UUID CartItem) {
        super(ErrorCode.CART_ITEM_NOT_FOUND, "Cart item not found with id: " + CartItem);
    }
}
