package ecomhub.authservice.common.exception.concrete.role.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class RoleIdRequiredException extends RequiredFieldMissingException {
    public RoleIdRequiredException() {
        super("ID vai trò");
    }
}
