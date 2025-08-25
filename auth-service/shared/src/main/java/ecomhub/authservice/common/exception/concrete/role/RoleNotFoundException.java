package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.ResourceNotFoundException;

import java.util.UUID;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException() {
        super("role");
    }
}
