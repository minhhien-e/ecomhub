package ecomhub.authservice.common.exception.concrete.account.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class AccountIdRequiredException extends RequiredFieldMissingException {
    public AccountIdRequiredException() {
        super("ID tài khoản");
    }
}
