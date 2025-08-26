package ecomhub.authservice.common.exception.concrete.valueobject.level;

import ecomhub.authservice.common.exception.abstracts.InvalidValueException;

public class InvalidLevelException extends InvalidValueException {
    private static final String format = "level '%d'";
    public InvalidLevelException(int level) {
        super(String.format(format, level), "account");
    }
}
