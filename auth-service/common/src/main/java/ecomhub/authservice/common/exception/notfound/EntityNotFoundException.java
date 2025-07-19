package ecomhub.authservice.common.exception.notfound;

import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.HttpException;

public class EntityNotFoundException extends HttpException {
    public EntityNotFoundException(String message) {
        super(404, ErrorCode.ENTITY_NOT_FOUND , String.format("%s không tồn tại", message));
    }
}
