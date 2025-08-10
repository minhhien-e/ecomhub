package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class RoleAlreadyExistsException extends ResourceAlreadyExistsException {
    public RoleAlreadyExistsException(String name) {
        super("Vai trò '" + name + "'");
    }
}
