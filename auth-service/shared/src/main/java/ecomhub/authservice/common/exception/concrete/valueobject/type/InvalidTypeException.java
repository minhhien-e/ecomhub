package ecomhub.authservice.common.exception.concrete.valueobject.type;

import ecomhub.authservice.common.exception.abstracts.InvalidValueException;

public class InvalidTypeException extends InvalidValueException {
    private static final String format = "type '%s'";

    public InvalidTypeException(String value, String domainName) {
        super(String.format(format, value), domainName);
    }
}
