package ecomhub.cart.exception.custom;

import ecomhub.cart.enums.ErrorCode;

public class MissingCustomerIdException extends HttpException{
    public MissingCustomerIdException() {
        super(ErrorCode.MISSING_CUSTOMER_ID, "Customer ID is required");
    }
}
