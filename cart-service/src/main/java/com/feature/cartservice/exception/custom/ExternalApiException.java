package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;
import lombok.Getter;

public class ExternalApiException extends HttpException {
    @Getter
    private final int externalHttpStatusCode;
    @Getter
    private final Integer externalErrorCode;


    public ExternalApiException(
            int extHttpStatusCode,
            Integer extErrorCode
    ) {
        super(ErrorCode.PRODUCT_SERVICE_REFUSE_VARIANT_ID, "variant id is not exit");
        this.externalHttpStatusCode = extHttpStatusCode;
        this.externalErrorCode = extErrorCode;
    }
}