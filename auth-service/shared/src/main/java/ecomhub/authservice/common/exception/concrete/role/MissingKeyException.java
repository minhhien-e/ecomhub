package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingKeyException extends RequiredFieldMissingException {
    public MissingKeyException(String domainName) {
        super(domainName + "'s key",true);
    }
}
