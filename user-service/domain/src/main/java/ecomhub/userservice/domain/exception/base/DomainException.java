package ecomhub.userservice.domain.exception.base;

import ecomhub.userservice.domain.enums.DomainErrorCode;

public abstract class DomainException extends RuntimeException {
    private final DomainErrorCode errorCode;

    public DomainException(String message, DomainErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainErrorCode getErrorCode() {
        return errorCode;
    }
}
