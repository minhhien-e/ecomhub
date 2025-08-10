package ecomhub.authservice.common.exception.concrete.valueobject.email;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingEmailException extends RequiredFieldMissingException {
    public MissingEmailException(String domainName) {
        super(String.format("email của %s", domainName));
    }
}
