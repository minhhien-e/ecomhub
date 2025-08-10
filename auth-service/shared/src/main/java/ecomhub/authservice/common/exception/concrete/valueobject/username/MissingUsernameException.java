package ecomhub.authservice.common.exception.concrete.valueobject.username;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingUsernameException extends RequiredFieldMissingException {
    public MissingUsernameException() {
        super("tên đăng nhập");
    }
}
