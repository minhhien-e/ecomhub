package ecomhub.authservice.common.exception.concrete.role.conflict;

import ecomhub.authservice.common.exception.conflict.ResourceAlreadyExistsException;

public class RoleAlreadyExistsException extends ResourceAlreadyExistsException {
    public RoleAlreadyExistsException(String name) {
        super("Vai trò với tên '" + name + "'");
    }
}
