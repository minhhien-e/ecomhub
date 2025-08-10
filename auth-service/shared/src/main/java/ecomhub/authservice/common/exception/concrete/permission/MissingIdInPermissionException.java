package ecomhub.authservice.common.exception.concrete.permission;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingIdInPermissionException extends RequiredFieldMissingException {
    public MissingIdInPermissionException() {
        super("ID quyền");
    }
}
