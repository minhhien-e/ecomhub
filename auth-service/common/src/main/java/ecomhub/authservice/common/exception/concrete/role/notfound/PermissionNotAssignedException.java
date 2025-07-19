package ecomhub.authservice.common.exception.concrete.role.notfound;


import ecomhub.authservice.common.exception.notfound.EntityNotFoundException;

import java.util.UUID;

public class PermissionNotAssignedException extends EntityNotFoundException {
    public PermissionNotAssignedException(UUID id) {
        super("Quyền trong vai trò với id '" + id + "'");
    }
}
