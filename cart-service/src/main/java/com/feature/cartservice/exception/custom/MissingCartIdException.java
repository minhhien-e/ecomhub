package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

public class MissingCartIdException extends HttpException{
    public MissingCartIdException() {
        super(ErrorCode.MISSING_CART_ID, "Cart ID is required");
    }
}
