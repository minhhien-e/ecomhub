package ecomhub.cartservice.cartservice.exception.custom;

import ecomhub.cartservice.cartservice.enums.ErrorCode;

public class InvalidQuantityException extends HttpException{
    public InvalidQuantityException(int quantity) {
        super(ErrorCode.INVALID_QUANTITY, "Quantity must be greater than 0, got: " + quantity);
    }
}
