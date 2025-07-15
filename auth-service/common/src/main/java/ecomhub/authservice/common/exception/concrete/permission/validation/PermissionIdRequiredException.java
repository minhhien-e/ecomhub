package ecomhub.authservice.common.exception.concrete.permission.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class PermissionIdRequiredException extends RequiredFieldMissingException {
    public PermissionIdRequiredException() {
        super("ID quyền");
    }
}
