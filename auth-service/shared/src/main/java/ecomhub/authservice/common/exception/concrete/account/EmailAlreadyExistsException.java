package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class EmailAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String format = "User with email '%s'";
    public EmailAlreadyExistsException(String email) {
        super(String.format(format, email));
    }

}
