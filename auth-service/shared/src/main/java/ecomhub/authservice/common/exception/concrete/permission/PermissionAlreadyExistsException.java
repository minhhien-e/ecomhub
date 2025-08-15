package ecomhub.authservice.common.exception.concrete.permission;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class PermissionAlreadyExistsException extends ResourceAlreadyExistsException {
    public PermissionAlreadyExistsException(String name, String key) {
        super("Quyền với tên '" + name + "' hoặc khóa '" + key + "'");
    }
}
