package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.concrete.role.PermissionAlreadyAssignedException;
import ecomhub.authservice.common.exception.concrete.role.PermissionNotAssignedException;
import ecomhub.authservice.domain.valueobject.Level;
import ecomhub.authservice.domain.valueobject.RoleStatus;
import ecomhub.authservice.domain.valueobject.RoleType;
import ecomhub.authservice.domain.valueobject.key.RoleKey;
import ecomhub.authservice.domain.valueobject.name.Name;
import ecomhub.authservice.domain.valueobject.name.RoleName;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static ecomhub.authservice.domain.constant.RoleStatusConstants.*;

public class Role {
    private final UUID id;
    private RoleName name;
    private final RoleKey key;
    private RoleType type;
    private RoleStatus status;
    private String description;
    private Level level;
    private final Set<Permission> permissions;

    public Role(UUID id, String name, String key, int level, String type, String status, String description, Set<Permission> permissions) {
        this.id = id;
        this.name = new RoleName(name);
        this.key = new RoleKey(key);
        this.type = new RoleType(type);
        this.description = description;
        this.permissions = new HashSet<>(permissions);
        this.status = new RoleStatus(status);
        this.level = new Level(level);
    }

    public Role(String name, String key, String type, String description, int level) {
        this.id = UUID.randomUUID();
        this.name = new RoleName(name);
        this.key = new RoleKey(key);
        this.type = new RoleType(type);
        this.description = description;
        this.permissions = new HashSet<>();
        this.status = new RoleStatus(ACTIVE);
        this.level = new Level(level);

    }

    public boolean hasPermission(String key) {
        return permissions.stream().anyMatch(p -> p.hasKey(key));
    }

    public void grantPermission(Permission permission) {
        if (this.permissions.contains(permission)) {
            throw new PermissionAlreadyAssignedException(permission.getName().getValue());
        }
        this.permissions.add(permission);
    }

    public void revokePermission(Permission permission) {
        if (!this.getPermissions().contains(permission)) {
            throw new PermissionNotAssignedException(permission.getName().getValue());
        }
        this.permissions.remove(permission);
    }

    // Status Management
    public void deactivate() {
        this.status = new RoleStatus(INACTIVE);
    }

    public void archive() {
        this.status = new RoleStatus(ARCHIVED);
    }

    public void activate() {
        this.status = new RoleStatus(ACTIVE);
    }

    public void delete() {
        this.status = new RoleStatus(DELETED);
    }

    //Update Information
    public void updateType(String newType) {
        this.type = new RoleType(newType);
    }

    public void updateName(String newName) {
        this.name = new RoleName(newName);
    }

    public void updateLevel(int newLevel) {
        this.level = new Level(newLevel);
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    public boolean greaterThan(Role role) {
        return this.level.getValue() > role.getLevel().getValue();
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

    public RoleKey getKey() {
        return key;
    }

    public RoleType getType() {
        return type;
    }

    public RoleStatus getStatus() {
        return status;
    }

    public Level getLevel() {
        return level;
    }
}
