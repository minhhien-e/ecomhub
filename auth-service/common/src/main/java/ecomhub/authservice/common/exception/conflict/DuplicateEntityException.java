package ecomhub.authservice.common.exception.conflict;

import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.HttpException;

public class DuplicateEntityException extends HttpException {
    public DuplicateEntityException(String message) {
        super(409, ErrorCode.DUPLICATE_ENTRY, String.format("%s đã tồn tại.", message));
    }
}
