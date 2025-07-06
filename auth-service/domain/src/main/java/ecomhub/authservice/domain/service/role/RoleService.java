package ecomhub.authservice.domain.service.role;

import ecomhub.authservice.domain.model.Role;

public interface RoleService {
    void addRole(Role role);
    boolean isRoleExists(String name);
}
