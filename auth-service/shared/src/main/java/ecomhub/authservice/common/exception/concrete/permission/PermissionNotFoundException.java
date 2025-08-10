package ecomhub.authservice.common.exception.concrete.permission;

import ecomhub.authservice.common.exception.abstracts.ResourceNotFoundException;

import java.util.UUID;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException(UUID id) {
        super("Quyền với ID '" + id+"'");
    }
}
