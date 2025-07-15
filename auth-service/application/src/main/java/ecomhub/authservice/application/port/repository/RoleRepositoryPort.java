package ecomhub.authservice.application.port.repository;

import ecomhub.authservice.domain.entity.Role;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepositoryPort {
    Role save(Role role);

    boolean existsByName(String name);

    Optional<UUID> findIdByName(String name);

    Optional<Role> findById(@NotNull UUID id);

    void grantPermissions(UUID roleId, Set<UUID> permissionIds);

    void revokePermissions(UUID roleId, Set<UUID> permissionIds);
}
