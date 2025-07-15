package ecomhub.authservice.common.exception.concrete.role.conflict;

import ecomhub.authservice.common.exception.conflict.DuplicateEntityException;

import java.util.UUID;

public class PermissionAlreadyAssignedException extends DuplicateEntityException {
    public PermissionAlreadyAssignedException(UUID id) {
        super("Quyền với id " + id + "'");
    }
}
