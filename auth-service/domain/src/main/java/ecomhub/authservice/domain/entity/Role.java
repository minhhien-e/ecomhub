package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.concrete.role.*;
import ecomhub.authservice.domain.valueobject.Level;
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
    private boolean active;
    private Level level;

    //region constructor
    public Role(UUID id, String name, String description, Set<Permission> permissions, boolean active, int level) {
        if (id == null) {
            throw new MissingIdInRoleException();
        }
        this.id = id;
        this.name = new Name(name, "vai trò");
        this.description = description;
        this.permissions = permissions;
        this.active = active;
        this.level = new Level(level);
    }

    public Role(String name, String description, int level) {
        this.id = UUID.randomUUID();
        this.name = new Name(name, "vai trò");
        this.description = description;
        this.permissions = new HashSet<>();
        this.active = true;
        this.level = new Level(level);

    }

    //endregion
//region getter
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

    public boolean isActive() {
        return active;
    }

    public Level getLevel() {
        return level;
    }

    //endregion
//region permission
    public void grantPermission(Permission permission) {
        if (permission == null) {
            throw new MissingPermissionException();
        }
        if (this.permissions.contains(permission)) {
            throw new PermissionAlreadyAssignedException(permission.getName().getValue());
        }
        this.permissions.add(permission);
    }

    public void revokePermission(Permission permission) {
        if (permission == null) {
            throw new MissingPermissionException();
        }
        if (!this.getPermissions().contains(permission)) {
            throw new PermissionNotAssignedException(permission.getName().getValue());
        }
        this.permissions.remove(permission);
    }

    //endregion
//region active
    public void setActive(boolean active, Role requesterRole) {
        if (requesterRole.canModify(this) && requesterRole.canInactiveRole())
            this.active = active;

    }

    private boolean canInactiveRole() {
        for (Permission permission : permissions) {
            if (permission.hasKey("role.delete")) return true;
        }
        return false;
    }

    private boolean canModify(Role editedRole) {
        return this.active && this.level.greaterThan(editedRole.level);
    }

    //endregion
}
