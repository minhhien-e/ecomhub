package ecomhub.authservice.common.exception.concrete.account.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class PhoneNumberRequiredException extends RequiredFieldMissingException {
    public PhoneNumberRequiredException() {
        super("Số điện thoại");
    }
}
