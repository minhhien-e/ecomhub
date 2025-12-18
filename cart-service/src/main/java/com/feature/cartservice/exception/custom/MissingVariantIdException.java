package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

public class MissingVariantIdException extends HttpException{
    public MissingVariantIdException() {

        super(ErrorCode.MISSING_VARIANT_ID, "Variant ID is required");
    }
}
