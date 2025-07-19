package ecomhub.authservice.common.exception.concrete.account.conflict;

import ecomhub.authservice.common.exception.conflict.DuplicateEntityException;

import java.util.UUID;

public class RoleAlreadyAssignedException extends DuplicateEntityException {
    public RoleAlreadyAssignedException(UUID id) {
        super("Vai trò với id '" + id + "'");
    }
}
