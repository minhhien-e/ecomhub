package ecomhub.authservice.common.exception.validation;

import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.HttpException;

public class RequiredFieldMissingException extends HttpException {
    public RequiredFieldMissingException(String message) {
        super(400, ErrorCode.REQUIRED_FIELD_MISSING, String.format("%s là bắt buộc.", message));
    }
}
