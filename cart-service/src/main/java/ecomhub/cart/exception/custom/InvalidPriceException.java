package ecomhub.cart.exception.custom;

import ecomhub.cart.enums.ErrorCode;

public class InvalidPriceException extends HttpException{
    public InvalidPriceException(double price) {
        super(ErrorCode.INVALID_PRICE, "Price cannot be negative, got: " + price);
    }
}
