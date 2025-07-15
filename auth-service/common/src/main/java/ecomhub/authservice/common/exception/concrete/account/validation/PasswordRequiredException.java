package ecomhub.authservice.common.exception.concrete.account.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class PasswordRequiredException extends RequiredFieldMissingException {
    public PasswordRequiredException() {
        super("Mật khẩu");
    }
}
