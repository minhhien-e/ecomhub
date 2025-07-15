package ecomhub.authservice.common.exception.concrete.account.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class UsernameRequiredException extends RequiredFieldMissingException {
    public UsernameRequiredException() {
        super("Tên đăng nhập");
    }
}
