package ecomhub.cartservice.cartservice.exception.custom;

import ecomhub.cartservice.cartservice.enums.ErrorCode;

public class InvalidPriceException extends HttpException{
    public InvalidPriceException(double price) {
        super(ErrorCode.INVALID_PRICE, "Price cannot be negative, got: " + price);
    }
}
