package ecomhub.authservice.common.exception.concrete.valueobject.type;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingTypeException extends RequiredFieldMissingException {
    public MissingTypeException(String domainName) {
        super("Please select type to create the " + domainName, false);
    }
}
