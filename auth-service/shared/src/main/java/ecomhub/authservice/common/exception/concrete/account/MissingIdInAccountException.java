package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingIdInAccountException extends RequiredFieldMissingException {
    public MissingIdInAccountException() {
        super("ID tài khoản");
    }
}
