package ecomhub.authservice.common.exception.concrete.valueobject.status;

import ecomhub.authservice.common.exception.abstracts.InvalidValueException;

public class InvalidStatusException extends InvalidValueException {
    private static final String format = "status '%s'";

    public InvalidStatusException(String value, String domainName) {
        super(String.format(format, value), domainName);
    }
}
