package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Role;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepositoryPort {
    //region save
    Role save(Role role);

    //endregion
    //region exists
    boolean existsByName(String name);

    //endregion
    //region find
    Optional<Role> findByName(String name);

    Optional<Role> findById(UUID id);

    //endregion
    //region update
    int updateActive(UUID id, boolean isActive);

    int updateDescription(UUID id, String newDescription);

    int updateLevel(UUID id, Integer newLevel);

    int updateName(UUID id, String newName);


    //endregion
    //region permission
    void grantPermissions(UUID roleId, Set<UUID> permissionIds);

    void revokePermissions(UUID roleId, Set<UUID> permissionIds);


    //endregion
}
