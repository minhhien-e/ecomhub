package ecomhub.authservice.common.exception.concrete.valueobject.email;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingEmailException extends RequiredFieldMissingException {
    public MissingEmailException() {
        super("email");
    }
}
