package ecomhub.userservice.domain.exception.user;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException(String message) {
        super(message, DomainErrorCode.USER_ALREADY_EXISTS);
    }
}
