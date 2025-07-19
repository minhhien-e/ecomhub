package ecomhub.authservice.common.exception.concrete.role.notfound;

import ecomhub.authservice.common.exception.notfound.ResourceNotFoundException;

import java.util.UUID;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(String name) {
        super("Vai trò với tên '" + name + "'");
    }

    public RoleNotFoundException(UUID id) {
        super("Vai trò với ID '" + id + "'");
    }
}
