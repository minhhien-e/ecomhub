package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class RequiredFieldMissingException extends HttpException {
    public RequiredFieldMissingException(String message) {
        super(400, ErrorCode.REQUIRED_FIELD_MISSING, String.format("Thiếu thông tin %s.", message));
    }
}
