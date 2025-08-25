package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class PhoneNumberAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String format = "User with phone number '%s'";
    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super(String.format(format, phoneNumber));
    }
}
