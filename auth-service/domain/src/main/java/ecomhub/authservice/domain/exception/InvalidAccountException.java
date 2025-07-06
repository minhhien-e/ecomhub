package ecomhub.authservice.domain.exception;

import ecomhub.authservice.common.exception.BusinessException;

public class InvalidAccountException extends BusinessException {
    public InvalidAccountException(String message) {
        super(message);
    }

}
