package ecomhub.authservice.common.exception.notfound;

import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.HttpException;

public class ResourceNotFoundException extends HttpException {
    public ResourceNotFoundException(String message) {
        super(404, ErrorCode.RESOURCE_NOT_FOUND,String.format("%s không tồn tại", message));
    }
}
