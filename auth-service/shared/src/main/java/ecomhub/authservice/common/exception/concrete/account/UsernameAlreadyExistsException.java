package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class UsernameAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String format = "User with username '%s'";
    public UsernameAlreadyExistsException(String email) {
        super(String.format(format, email));
    }
}
