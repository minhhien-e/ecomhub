package ecomhub.authservice.domain.service.permission;

import ecomhub.authservice.domain.model.Permission;

public interface PermissionService {
    void addPermission(Permission permission);
    boolean isPermissionExists(String name);
}
