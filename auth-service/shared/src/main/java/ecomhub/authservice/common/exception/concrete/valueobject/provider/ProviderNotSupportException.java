package ecomhub.authservice.common.exception.concrete.valueobject.provider;

import ecomhub.authservice.common.exception.abstracts.NotSupportException;

public class ProviderNotSupportException extends NotSupportException {
    public ProviderNotSupportException(String provider) {
        super("Phương thức đăng nhập '" + provider + "'");
    }
}
