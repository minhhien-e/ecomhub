package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class EntityNotFoundException extends HttpException {
    public EntityNotFoundException(String message) {
        super(404, ErrorCode.ENTITY_NOT_FOUND , String.format("%s không tồn tại", message));
    }
}
