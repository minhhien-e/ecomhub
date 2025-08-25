package ecomhub.authservice.common.exception.concrete.permission;

import ecomhub.authservice.common.exception.abstracts.ResourceNotFoundException;

import java.util.UUID;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException() {
        super("permission");
    }
}
