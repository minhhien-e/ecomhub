package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.model.Permission;

public interface PermissionRepository {
    Permission save(Permission permission);

    boolean existsByName(String name);
}
