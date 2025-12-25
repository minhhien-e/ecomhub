package ecomhub.cart.exception.custom;

import ecomhub.cart.enums.ErrorCode;

public class MissingVariantIdException extends HttpException{
    public MissingVariantIdException() {

        super(ErrorCode.MISSING_VARIANT_ID, "Variant ID is required");
    }
}
