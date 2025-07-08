package ecomhub.authservice.application.ports.repositories;

import ecomhub.authservice.domain.entities.Permission;

public interface PermissionRepository {
    Permission save(Permission permission);

    boolean existsByName(String name);
}
