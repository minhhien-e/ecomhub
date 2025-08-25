package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class DuplicateEntityException extends HttpException {
    public DuplicateEntityException(String message) {
        super(409, ErrorCode.DUPLICATE_ENTRY, String.format("The %s already exists.", message));
    }
}
