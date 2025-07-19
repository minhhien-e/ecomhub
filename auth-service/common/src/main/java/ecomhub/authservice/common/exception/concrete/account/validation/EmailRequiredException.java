package ecomhub.authservice.common.exception.concrete.account.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class EmailRequiredException extends RequiredFieldMissingException {
    public EmailRequiredException() {
        super("Email");
    }
}
