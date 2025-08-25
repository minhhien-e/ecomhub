package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    boolean existsByNameOrKey(String name, String key);

    boolean existsByKey(String key);

    boolean existsById(UUID id);

    List<Permission> findAllByKeyIn(Set<String> permissionKeys);

    List<Permission> findAll();

    void deleteById(UUID id);

    int updateDescription(UUID id, String newDescription);

    int updateName(UUID id, String newName);

    Permission getById(UUID permissionId);

    Optional<Permission> findByKey(String permissionKey);
}
