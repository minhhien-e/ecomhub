package ecomhub.authservice.domain.entity;


import ecomhub.authservice.common.exception.concrete.permission.validation.PermissionIdRequiredException;
import ecomhub.authservice.common.exception.concrete.permission.validation.PermissionNameRequiredException;

import java.util.Optional;
import java.util.UUID;

public class Permission {
    private UUID id;
    private String name;
    private String description;

    /**
     * Lấy dữ liệu từ db
     */
    public Permission(UUID id, String name, String description) {
        validateForLoad(id, name);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Tạo permission
     */
    public Permission(String name, String description) {
        validateForCreate(name);
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
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

    //Kiểm tra thông tin để tạo
    private void validateForCreate(String name) {
        if (name == null || name.isBlank()) {
            throw new PermissionNameRequiredException();
        }
    }

    private void validateForLoad(UUID id, String name) {
        validateForCreate(name);
        if (id == null) {
            throw new PermissionIdRequiredException();
        }
    }
}
