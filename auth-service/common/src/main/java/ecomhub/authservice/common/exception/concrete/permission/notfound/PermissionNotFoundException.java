package ecomhub.authservice.common.exception.concrete.permission.notfound;

import ecomhub.authservice.common.exception.notfound.ResourceNotFoundException;

import java.util.UUID;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException(UUID id) {
        super("Quyền với ID '" + id+"'");
    }
}
