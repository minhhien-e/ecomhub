package ecomhub.authservice.common.exception.concrete.valueobject.name;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingNameException extends RequiredFieldMissingException {
    public MissingNameException(String domainName) {
        super("tên của " + domainName);
    }
}
