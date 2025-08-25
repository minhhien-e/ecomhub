package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.DuplicateEntityException;

public class RoleAlreadyAssignedException extends DuplicateEntityException {
    public RoleAlreadyAssignedException(String name) {
        super("role '" + name);
    }
}
