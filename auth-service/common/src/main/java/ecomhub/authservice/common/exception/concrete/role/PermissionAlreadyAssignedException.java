package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.DuplicateEntityException;

public class PermissionAlreadyAssignedException extends DuplicateEntityException {
    public PermissionAlreadyAssignedException(String name) {
        super("Quyền '" + name + "'");
    }
}
