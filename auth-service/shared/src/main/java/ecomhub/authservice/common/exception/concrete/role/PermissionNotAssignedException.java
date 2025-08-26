package ecomhub.authservice.common.exception.concrete.role;


import ecomhub.authservice.common.exception.abstracts.EntityNotFoundException;

public class PermissionNotAssignedException extends EntityNotFoundException {
    public PermissionNotAssignedException(String name) {
        super("permission '" + name + "'");
    }
}
