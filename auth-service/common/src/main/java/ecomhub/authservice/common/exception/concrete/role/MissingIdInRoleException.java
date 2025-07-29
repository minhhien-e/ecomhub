package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingIdInRoleException extends RequiredFieldMissingException {
    public MissingIdInRoleException() {
        super("Id vai trò");

    }
}
