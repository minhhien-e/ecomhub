package ecomhub.authservice.adapter.output.mapper.permission;

import ecomhub.authservice.domain.entities.Permission;
import ecomhub.authservice.adapter.output.persistence.entity.PermissionEntity;

public class PermissionPersistenceMapper {
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
