package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.concrete.permission.MissingIdInPermissionException;
import ecomhub.authservice.domain.valueobject.Name;
import ecomhub.authservice.domain.valueobject.PermissionKey;

import java.util.Optional;
import java.util.UUID;

public class Permission {
    private UUID id;
    private Name name;
    private PermissionKey key;
    private String description;

    /**
     * Lấy dữ liệu từ db
     */
    public Permission(UUID id, String name, String key, String description) {
        if (id == null) {
            throw new MissingIdInPermissionException();
        }
        this.id = id;
        this.name = new Name(name, "quyền");
        this.key = new PermissionKey(key);
        this.description = description;
    }

    /**
     * Tạo permission
     */
    public Permission(String name, String key, String description) {
        this.id = UUID.randomUUID();
        this.name = new Name(name, "quyền");
        this.key = new PermissionKey(key);
        this.description = description;
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


    public boolean hasKey(String key) {
        return this.key.equals(new PermissionKey(key));
    }
}
