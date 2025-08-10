package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class PhoneNumberAlreadyExistsException extends ResourceAlreadyExistsException {
    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Số điện thoại " + phoneNumber);
    }
}
