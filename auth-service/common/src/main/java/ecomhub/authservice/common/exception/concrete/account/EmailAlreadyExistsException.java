package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class EmailAlreadyExistsException extends ResourceAlreadyExistsException {
    public EmailAlreadyExistsException(String email) {
        super("Email đã " + email);
    }

}
