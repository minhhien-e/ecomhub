package ecomhub.authservice.common.exception.concrete.role.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class RoleNameRequiredException extends RequiredFieldMissingException {
    public RoleNameRequiredException() {
        super("Tên vai trò");
    }
}
