package ecomhub.authservice.common.exception.concrete.valueobject.password;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingPasswordException extends RequiredFieldMissingException {
    public MissingPasswordException() {
        super("mật khẩu");
    }
}
