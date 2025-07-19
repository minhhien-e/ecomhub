package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.concrete.account.conflict.RoleAlreadyAssignedException;
import ecomhub.authservice.common.exception.concrete.account.notfound.RoleNotAssignedException;
import ecomhub.authservice.common.exception.concrete.role.business.NoPermissionAssignedException;
import ecomhub.authservice.common.exception.concrete.role.validation.RoleIdRequiredException;
import ecomhub.authservice.common.exception.concrete.role.validation.RoleNameRequiredException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Role {
    private UUID id;
    private String name;
    private String description;
    private Set<UUID> permissionIds;

    /**
     * Lấy thông tin từ db
     */
    public Role(UUID id, String name, String description, Set<UUID> permissionIds) {
        validateForLoad(id, name, permissionIds);
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissionIds = permissionIds;
    }

    public Role(String name, String description) {
        validateForCreate(name);
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.permissionIds = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Set<UUID> getPermissionIds() {
        return permissionIds;
    }

    public void grantPermission(UUID permissionId) {
        if (permissionId == null) {
            throw new RoleIdRequiredException();
        }
        if (this.permissionIds.contains(permissionId)) {
            throw new RoleAlreadyAssignedException(permissionId);
        }
        this.permissionIds.add(permissionId);
    }

    public void revokePermission(UUID permissionId) {
        if (permissionId == null) {
            throw new RoleIdRequiredException();
        }
        if (!this.permissionIds.contains(permissionId)) {
            throw new RoleNotAssignedException(permissionId);
        }
        this.permissionIds.remove(permissionId);
    }

    /**
     * Kiểm tra để tạo
     */
    private void validateForCreate(String name) {
        if (name == null || name.isBlank()) {
            throw new RoleNameRequiredException();
        }
    }

    private void validateForLoad(UUID id, String name, Set<UUID> permissionIds) {
        validateForCreate(name);
        if (id == null) {
            throw new RoleIdRequiredException();
        }
        if (permissionIds.isEmpty()) {
            throw new NoPermissionAssignedException();
        }
    }
}
