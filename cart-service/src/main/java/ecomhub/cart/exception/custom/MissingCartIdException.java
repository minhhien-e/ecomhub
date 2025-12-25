package ecomhub.cart.exception.custom;

import ecomhub.cart.enums.ErrorCode;

public class MissingCartIdException extends HttpException{
    public MissingCartIdException() {
        super(ErrorCode.MISSING_CART_ID, "Cart ID is required");
    }
}
