package ecomhub.authservice.common.exception;

import ecomhub.authservice.common.enums.ErrorCode;

public abstract class HttpException extends RuntimeException {
    private final int statusCode;
    private final ErrorCode errorCode;

    public HttpException(int statusCode, ErrorCode errorCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode.name();
    }

}
