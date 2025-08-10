package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    boolean existsByName(String name);

    List<Permission> findAllByKeyIn(Set<String> permissionKeys);
}
