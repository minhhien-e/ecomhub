package ecomhub.cartservice.cartservice.exception.custom;

import ecomhub.cartservice.cartservice.enums.ErrorCode;

public class MissingCustomerIdException extends HttpException{
    public MissingCustomerIdException() {
        super(ErrorCode.MISSING_CUSTOMER_ID, "Customer ID is required");
    }
}
