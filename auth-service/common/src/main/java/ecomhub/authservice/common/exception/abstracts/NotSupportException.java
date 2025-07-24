package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class NotSupportException extends HttpException {
    public NotSupportException(String message) {
        super(404, ErrorCode.RESOURCE_NOT_FOUND, String.format("%s không được hỗ trợ", message));
    }
}
