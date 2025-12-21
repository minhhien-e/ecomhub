package ecomhub.cartservice.cartservice.exception.custom;

import ecomhub.cartservice.cartservice.enums.ErrorCode;

public class MissingVariantIdException extends HttpException{
    public MissingVariantIdException() {

        super(ErrorCode.MISSING_VARIANT_ID, "Variant ID is required");
    }
}
