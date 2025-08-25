package ecomhub.authservice.common.exception.concrete.valueobject.phonenumber;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingPhoneNumberException extends RequiredFieldMissingException {
    public MissingPhoneNumberException() {
        super("số điện thoại");
    }
}
