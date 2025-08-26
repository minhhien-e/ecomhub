package ecomhub.authservice.common.exception.concrete.valueobject.provider;

import ecomhub.authservice.common.exception.abstracts.InvalidValueException;

public class InvalidProviderException extends InvalidValueException {
    private static final String format = "provider '%s'";
    public InvalidProviderException(String provider) {
        super(String.format(format, provider), "account");
    }
}
