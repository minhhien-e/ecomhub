package ecomhub.userservice.domain.exception.user;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;

public class UserAccessDeniedException extends DomainException {
    public UserAccessDeniedException() {
        super("Access denied", DomainErrorCode.ACCESS_DENIED);
    }
}
