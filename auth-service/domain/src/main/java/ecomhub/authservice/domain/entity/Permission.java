package ecomhub.authservice.domain.entity;


import ecomhub.authservice.domain.valueobject.key.PermissionKey;
import ecomhub.authservice.domain.valueobject.name.Name;
import ecomhub.authservice.domain.valueobject.name.PermissionName;

import java.util.Optional;
import java.util.UUID;

public class Permission {
    private final UUID id;
    private PermissionName name;
    private final PermissionKey key;
    private String description;

    public Permission(UUID id, String name, String key, String description) {
        this.id = id;
        this.name = new PermissionName(name);
        this.key = new PermissionKey(key);
        this.description = description;
    }

    public Permission(String name, String key, String description) {
        this.id = UUID.randomUUID();
        this.name = new PermissionName(name);
        this.key = new PermissionKey(key);
        this.description = description;
    }

    public boolean hasKey(String key) {
        return this.key.equals(new PermissionKey(key));
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    public void updateName(String newName) {
        this.name = new PermissionName(newName);
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

    public PermissionKey getKey() {
        return key;
    }
}
