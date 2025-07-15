package ecomhub.authservice.infrastructure.data.persistence.mapper;

import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.infrastructure.data.persistence.entity.PermissionEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RolePermissionEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.id.RolePermissionId;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RolePersistenceMapper {
    public static RoleEntity toEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription().orElse(null))
                .rolePermissions(convertPermissionToEntities(role))
                .build();
    }

    public static Role toDomain(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(),
                roleEntity.getName(),
                roleEntity.getDescription(),
                convertPermissionToIds(roleEntity.getRolePermissions())
        );
    }

    /**
     * Chuyển Permission thành id
     */
    private static Set<UUID> convertPermissionToIds(Set<RolePermissionEntity> permissions) {
        return permissions
                .stream()
                .map(permission -> permission.getPermission().getId())
                .collect(java.util.stream.Collectors.toSet());
    }

    private static Set<RolePermissionEntity> convertPermissionToEntities(Role role) {
        return role.getPermissionIds()
                .stream()
                .map(id -> {
                    RolePermissionId rolePermissionId = new RolePermissionId(role.getId(), id);
                    return RolePermissionEntity.builder().id(rolePermissionId)
                            .permission(PermissionEntity.builder()
                                    .id(id)
                                    .build())
                            .role(RoleEntity.builder()
                                    .id(role.getId())
                                    .build())
                            .build();
                })
                .collect(Collectors.toSet());
    }
}
