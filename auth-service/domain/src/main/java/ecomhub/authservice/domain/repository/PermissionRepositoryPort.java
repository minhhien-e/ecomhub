package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PermissionRepositoryPort {
    boolean existsByKey(String key);

    boolean existsById(UUID id);

    List<Permission> findAllByKeyIn(Set<String> permissionKeys);

    List<Permission> findAll();

    int updateDescription(Permission permission);

    int updateName(Permission permission);

    Permission getById(UUID permissionId);

    Optional<Permission> findByKey(String permissionKey);
}
