package ecomhub.authservice.common.exception.concrete.valueobject.permissionkey;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingPermissionKeyException extends RequiredFieldMissingException {
    public MissingPermissionKeyException() {
        super("permission key", true);
    }
}
