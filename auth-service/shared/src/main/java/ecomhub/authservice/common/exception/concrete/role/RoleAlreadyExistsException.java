package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class RoleAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String format = "Role with %s '%s'";

    public RoleAlreadyExistsException(String value, boolean nameExists) {
        super(String.format(format, nameExists ? "name" : "key", value));
    }
}
