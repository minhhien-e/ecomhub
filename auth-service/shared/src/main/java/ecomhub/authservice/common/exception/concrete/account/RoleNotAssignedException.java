package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.EntityNotFoundException;

public class RoleNotAssignedException extends EntityNotFoundException {
    public RoleNotAssignedException(String name) {
        super("role '" + name + "'");
    }
}
