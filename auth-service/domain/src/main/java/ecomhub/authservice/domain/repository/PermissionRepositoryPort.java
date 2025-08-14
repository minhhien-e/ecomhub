package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    boolean existsByName(String name);

    boolean existsById(UUID id);

    List<Permission> findAllByKeyIn(Set<String> permissionKeys);

    void deleteById(UUID id);

    int updateDescription(UUID id, String newDescription);

    int updateName(UUID id, String newName);

    Optional<Permission> findById(UUID permissionId);
}
