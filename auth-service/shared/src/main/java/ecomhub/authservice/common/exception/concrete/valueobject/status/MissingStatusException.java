package ecomhub.authservice.common.exception.concrete.valueobject.status;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingStatusException extends RequiredFieldMissingException {
    public MissingStatusException(String domainName) {
        super("Please select status to create the " + domainName, false);
    }
}
