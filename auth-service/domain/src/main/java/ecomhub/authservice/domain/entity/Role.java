package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.concrete.role.*;
import ecomhub.authservice.domain.valueobject.Name;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Role {
    private UUID id;
    private Name name;
    private String description;
    private Set<Permission> permissions;

    /**
     * Lấy thông tin từ db
     */
    public Role(UUID id, String name, String description, Set<Permission> permissions) {
        validateForLoad(id, name, permissions);
        this.id = id;
        this.name = new Name(name, "vai trò");
        this.description = description;
        this.permissions = permissions;
    }

    public Role(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = new Name(name, "vai trò");
        this.description = description;
        this.permissions = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Set<Permission> getPermissions() {
        return Set.copyOf(permissions);
    }

    public void grantPermission(Permission permission) {
        if (permission == null) {
            throw new MissingPermissionException();
        }
        if (this.permissions.contains(permission)) {
            throw new PermissionAlreadyAssignedException(permission.getName());
        }
        this.permissions.add(permission);
    }

    public void revokePermission(Permission permission) {
        if (permission == null) {
            throw new MissingPermissionException();
        }
        if (!this.permissions.contains(permission)) {
            throw new PermissionNotAssignedException(permission.getName());
        }
        this.permissions.remove(permission);
    }


    private void validateForLoad(UUID id, String name, Set<Permission> permissions) {
        if (id == null) {
            throw new MissingIdInRoleException();
        }
        if (permissions.isEmpty()) {
            throw new NoPermissionAssignedException();
        }
    }
}
