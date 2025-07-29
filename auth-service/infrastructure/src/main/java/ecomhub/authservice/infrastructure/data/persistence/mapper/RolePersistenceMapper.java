package ecomhub.authservice.infrastructure.data.persistence.mapper;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.infrastructure.data.persistence.entity.PermissionEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RolePermissionEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.id.RolePermissionId;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RolePersistenceMapper {
    public static RoleEntity toEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName().getValue())
                .description(role.getDescription().orElse(null))
                .rolePermissions(convertPermissionToEntities(role))
                .build();
    }

    public static Role toDomain(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(),
                roleEntity.getName(),
                roleEntity.getDescription(),
                convertPermissionToDomain(roleEntity.getRolePermissions())
        );

    }
    private static Set<Permission> convertPermissionToDomain(Set<RolePermissionEntity> permissions) {
        return permissions
                .stream()
                .map(permission -> PermissionPersistenceMapper.toDomain(permission.getPermission()))
                .collect(java.util.stream.Collectors.toSet());
    }

    private static Set<RolePermissionEntity> convertPermissionToEntities(Role role) {
        return role.getPermissions()
                .stream()
                .map(permission -> {
                    RolePermissionId rolePermissionId = new RolePermissionId(role.getId(), permission.getId());
                    return RolePermissionEntity.builder().id(rolePermissionId)
                            .permission(PermissionEntity.builder()
                                    .id(permission.getId())
                                    .build())
                            .role(RoleEntity.builder()
                                    .id(role.getId())
                                    .build())
                            .build();
                })
                .collect(Collectors.toSet());
    }
}
