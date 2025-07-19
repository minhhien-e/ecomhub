package ecomhub.authservice.common.exception.concrete.account.conflict;

import ecomhub.authservice.common.exception.conflict.ResourceAlreadyExistsException;

public class EmailAlreadyExistsException extends ResourceAlreadyExistsException {
    public EmailAlreadyExistsException(String email) {
        super("Email đã " + email);
    }

}
