package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PermissionRepositoryPort {
    //region save
    Permission save(Permission permission);

    //endregion
    //region exists
    boolean existsByNameOrKey(String name, String key);

    boolean existsByKey(String key);

    boolean existsById(UUID id);

    //endregion
    //region find all
    List<Permission> findAllByKeyIn(Set<String> permissionKeys);

    List<Permission> findAll();

    //endregion
    //region delete
    void deleteById(UUID id);

    //endregion
    //region update
    int updateDescription(UUID id, String newDescription);

    int updateName(UUID id, String newName);

    //endregion
    //region find
    Optional<Permission> findById(UUID permissionId);

    Optional<Permission> findByKey(String permissionKey);
    //endregion
}
