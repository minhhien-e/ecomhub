package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

public class InvalidQuantityException extends HttpException{
    public InvalidQuantityException(int quantity) {
        super(ErrorCode.INVALID_QUANTITY, "Quantity must be greater than 0, got: " + quantity);
    }
}
