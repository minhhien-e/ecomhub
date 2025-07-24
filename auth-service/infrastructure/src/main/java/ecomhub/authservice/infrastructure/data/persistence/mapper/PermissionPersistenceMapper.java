package ecomhub.authservice.infrastructure.data.persistence.mapper;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.infrastructure.data.persistence.entity.PermissionEntity;

public class PermissionPersistenceMapper {
    public static PermissionEntity toEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId())
                .name(permission.getName().getValue())
                .key(permission.getKey().getValue())
                .description(permission.getDescription().orElse(null))
                .build();
    }

    public static Permission toDomain(PermissionEntity permissionEntity) {
        return new Permission(permissionEntity.getKey(),
                permissionEntity.getName(),
                permissionEntity.getDescription());
    }
}
