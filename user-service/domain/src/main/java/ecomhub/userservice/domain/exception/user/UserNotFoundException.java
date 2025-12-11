package ecomhub.userservice.domain.exception.user;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;

import java.util.UUID;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(UUID userId) {
        super("User with id " + userId + " not found", DomainErrorCode.USER_NOT_FOUND);
    }
}
