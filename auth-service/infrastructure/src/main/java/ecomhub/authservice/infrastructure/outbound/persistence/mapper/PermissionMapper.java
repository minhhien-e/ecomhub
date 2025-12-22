package ecomhub.authservice.infrastructure.outbound.persistence.mapper;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.PermissionEntity;

public class PermissionMapper {
    public static PermissionEntity toEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId())
                .name(permission.getName().getValue())
                .key(permission.getKey().getValue())
                .description(permission.getDescription().orElse(null))
                .build();
    }

    public static Permission toDomain(PermissionEntity permissionEntity) {
        return new Permission(permissionEntity.getName(),
                permissionEntity.getKey(),
                permissionEntity.getDescription());
    }
}
