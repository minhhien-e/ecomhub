package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepositoryPort {
    Role save(Role role);

    boolean existsByName(String name);

    Role getByRoleKey(String key);

    Optional<Role> findById(UUID id);

    int updateActive(UUID id, boolean isActive);

    int updateDescription(UUID id, String newDescription);

    int updateLevel(UUID id, Integer newLevel);

    int updateName(UUID id, String newName);

    void grantPermissions(UUID roleId, Set<UUID> permissionIds);

    void revokePermissions(UUID roleId, UUID permissionId);

    List<Role> findAll();
}
