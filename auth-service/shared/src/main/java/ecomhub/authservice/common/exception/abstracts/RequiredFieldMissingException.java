package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class RequiredFieldMissingException extends HttpException {
    private static final String format = "Please enter %s.";

    public RequiredFieldMissingException(String message, boolean usedFormat) {
        super(400, ErrorCode.REQUIRED_FIELD_MISSING, usedFormat ? String.format(format, message) : message);
    }
}
