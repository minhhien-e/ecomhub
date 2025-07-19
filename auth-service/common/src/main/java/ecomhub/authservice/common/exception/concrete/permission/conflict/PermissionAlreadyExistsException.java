package ecomhub.authservice.common.exception.concrete.permission.conflict;

import ecomhub.authservice.common.exception.conflict.ResourceAlreadyExistsException;

public class PermissionAlreadyExistsException extends ResourceAlreadyExistsException {
    public PermissionAlreadyExistsException(String name) {
        super("Quyền với tên '" + name + "'");
    }
}
