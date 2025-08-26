package ecomhub.authservice.common.exception.concrete.valueobject.email;

import ecomhub.authservice.common.exception.abstracts.InvalidFormatException;

public class InvalidEmailFormatException extends InvalidFormatException {
    public InvalidEmailFormatException(String email) {
        super("Invalid email format: '" + email + "'. Please enter a valid email address.");
    }
}
