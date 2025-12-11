package ecomhub.authservice.common.exception.concrete.permission;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class PermissionAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String format = "Permission with %s '%s'";

    public PermissionAlreadyExistsException(String value, boolean nameExists) {
        super(String.format(format, nameExists ? "name" : "key", value));
    }
}
