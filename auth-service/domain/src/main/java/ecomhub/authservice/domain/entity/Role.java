package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.concrete.role.*;
import ecomhub.authservice.domain.valueobject.Level;
import ecomhub.authservice.domain.valueobject.name.Name;
import ecomhub.authservice.domain.valueobject.name.RoleName;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Role {
    private final UUID id;
    private RoleName name;
    private String description;
    private final Set<Permission> permissions;
    private boolean active;
    private Level level;

    public Role(UUID id, String name, String description, Set<Permission> permissions, boolean active, int level) {
        this.id = id;
        this.name = new RoleName(name);
        this.description = description;
        this.permissions = new HashSet<>(permissions);
        this.active = active;
        this.level = new Level(level);
    }

    public Role(String name, String description, int level) {
        this.id = UUID.randomUUID();
        this.name = new RoleName(name);
        this.description = description;
        this.permissions = new HashSet<>();
        this.active = true;
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

    public void deactivate() {
        this.active = false;

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

    public boolean isActive() {
        return active;
    }

    public Level getLevel() {
        return level;
    }
}
