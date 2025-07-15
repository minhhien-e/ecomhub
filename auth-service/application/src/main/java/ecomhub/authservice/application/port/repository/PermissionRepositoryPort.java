package ecomhub.authservice.application.port.repository;

import ecomhub.authservice.domain.entity.Permission;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    boolean existsByName(String name);
}
