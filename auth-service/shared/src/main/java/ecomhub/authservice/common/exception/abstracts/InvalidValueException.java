package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class InvalidValueException extends HttpException {
    private static final String format = "Invalid %s for %s";

    public InvalidValueException(String fieldAndValue, String domainName) {
        super(400, ErrorCode.INVALID_VALUE, String.format(format, fieldAndValue, domainName));
    }
}
