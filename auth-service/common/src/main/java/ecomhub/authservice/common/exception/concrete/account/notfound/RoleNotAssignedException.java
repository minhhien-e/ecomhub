package ecomhub.authservice.common.exception.concrete.account.notfound;

import ecomhub.authservice.common.exception.notfound.EntityNotFoundException;

import java.util.UUID;

public class RoleNotAssignedException extends EntityNotFoundException {
    public RoleNotAssignedException(UUID id) {
        super("Vai trò với Id '" + id + "'");
    }
}
