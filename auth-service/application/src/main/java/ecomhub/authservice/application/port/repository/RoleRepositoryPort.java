package ecomhub.authservice.application.port.repository;

import ecomhub.authservice.domain.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepositoryPort {
    Role save(Role role);

    boolean existsByName(String name);

    Optional<Role> findByName(String name);
    List<Role> findByAccountIdAndHigherLevelThan(UUID accountId, int level);

    Optional<Role> findById(UUID id);

    int updateActive(UUID id, boolean isActive);

    void grantPermissions(UUID roleId, Set<UUID> permissionIds);

    void revokePermissions(UUID roleId, Set<UUID> permissionIds);
}
