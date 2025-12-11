package ecomhub.userservice.domain.exception.user;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;

public class InvalidUsernameException extends DomainException {
    public InvalidUsernameException() {
        super("Username cannot be null or empty", DomainErrorCode.INVALID_USERNAME);
    }
}
