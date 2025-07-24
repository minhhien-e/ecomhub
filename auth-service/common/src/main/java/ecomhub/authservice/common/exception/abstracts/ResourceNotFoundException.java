package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class ResourceNotFoundException extends HttpException {
    public ResourceNotFoundException(String message) {
        super(404, ErrorCode.RESOURCE_NOT_FOUND, String.format("%s không tồn tại", message));
    }
}
