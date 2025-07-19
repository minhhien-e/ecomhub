package ecomhub.authservice.common.exception.concrete.account.conflict;

import ecomhub.authservice.common.exception.conflict.ResourceAlreadyExistsException;

public class PhoneNumberAlreadyExistsException extends ResourceAlreadyExistsException {
    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Số điện thoại " + phoneNumber);
    }
}
