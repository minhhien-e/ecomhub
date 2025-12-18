package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

public class InvalidPriceException extends HttpException{
    public InvalidPriceException(double price) {
        super(ErrorCode.INVALID_PRICE, "Price cannot be negative, got: " + price);
    }
}
