package ecomhub.cartservice.cartservice.exception.custom;

import ecomhub.cartservice.cartservice.enums.ErrorCode;

public class MissingCartIdException extends HttpException{
    public MissingCartIdException() {
        super(ErrorCode.MISSING_CART_ID, "Cart ID is required");
    }
}
