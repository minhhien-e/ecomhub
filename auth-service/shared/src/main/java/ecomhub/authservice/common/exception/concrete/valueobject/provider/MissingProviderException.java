package ecomhub.authservice.common.exception.concrete.valueobject.provider;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingProviderException extends RequiredFieldMissingException {
    public MissingProviderException() {
        super("Phương thức đăng nhập");
    }
}
