package ecomhub.authservice.infrastructure.mapper;

import ecomhub.authservice.domain.model.Permission;
import ecomhub.authservice.infrastructure.persistence.entity.PermissionEntity;

public class PermissionMapper {
    public static PermissionEntity toEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }

    public static Permission toDomain(PermissionEntity permissionEntity) {
        return Permission.builder()
                .id(permissionEntity.getId())
                .name(permissionEntity.getName())
                .description(permissionEntity.getDescription())
                .build();
    }
}
