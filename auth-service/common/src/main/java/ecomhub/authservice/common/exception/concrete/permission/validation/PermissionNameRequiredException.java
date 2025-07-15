package ecomhub.authservice.common.exception.concrete.permission.validation;

import ecomhub.authservice.common.exception.validation.RequiredFieldMissingException;

public class PermissionNameRequiredException extends RequiredFieldMissingException {
    public PermissionNameRequiredException() {
        super("Tên quyền");
    }
}
