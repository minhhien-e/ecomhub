package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class InvalidFormatException extends HttpException {
    public InvalidFormatException(String message) {
        super(400, ErrorCode.INVALID_FORMAT, message);
    }
}
