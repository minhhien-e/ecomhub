package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.ResourceNotFoundException;

import java.util.UUID;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(String name) {
        super("Vai trò với tên '" + name + "'");
    }

    public RoleNotFoundException(UUID id) {
        super("Vai trò với ID '" + id + "'");
    }
}
