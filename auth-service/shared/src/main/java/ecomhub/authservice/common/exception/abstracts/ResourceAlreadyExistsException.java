package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class ResourceAlreadyExistsException extends HttpException {
    public ResourceAlreadyExistsException(String message) {
        super(409, ErrorCode.RESOURCE_ALREADY_EXISTS, String.format("%s already exists.", message));
    }
}
