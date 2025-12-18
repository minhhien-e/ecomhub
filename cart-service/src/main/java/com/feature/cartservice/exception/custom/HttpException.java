package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;
import lombok.Getter;

public abstract class HttpException extends RuntimeException {

    @Getter
    private final int statusCode;
    private final ErrorCode errorCode;

    public HttpException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = errorCode.getCode();
    }

    public int getErrorCode() {
        return errorCode.getCode();
    }

    public String getErrorMessage() {
        return errorCode.getMessage();
    }
}
