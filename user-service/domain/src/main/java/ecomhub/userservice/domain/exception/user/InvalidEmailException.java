package ecomhub.userservice.domain.exception.user;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException() {
        super("Email cannot be null or empty", DomainErrorCode.INVALID_EMAIL);
    }
}
