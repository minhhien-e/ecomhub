package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

public class MissingCustomerIdException extends HttpException{
    public MissingCustomerIdException() {
        super(ErrorCode.MISSING_CUSTOMER_ID, "Customer ID is required");
    }
}
