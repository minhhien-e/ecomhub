package ecomhub.authservice.common.exception.concrete.valueobject.provider;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingProviderException extends RequiredFieldMissingException {
    public MissingProviderException() {
        super("Please select a provider to assign to the account", false);
    }
}
