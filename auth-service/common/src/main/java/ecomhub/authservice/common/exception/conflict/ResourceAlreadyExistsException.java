package ecomhub.authservice.common.exception.conflict;

import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.HttpException;

public class ResourceAlreadyExistsException extends HttpException {
    public ResourceAlreadyExistsException(String message) {
        super(409, ErrorCode.RESOURCE_ALREADY_EXISTS, String.format("%s đã tồn tại.", message));
    }
}
