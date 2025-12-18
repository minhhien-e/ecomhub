package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

import java.util.UUID;

public class CustomerNotFoundException extends HttpException {
    public CustomerNotFoundException(UUID customerId) {
        super(ErrorCode.CUSTOMER_NOT_FOUND, "Customer not found with id: " + customerId);
    }
}