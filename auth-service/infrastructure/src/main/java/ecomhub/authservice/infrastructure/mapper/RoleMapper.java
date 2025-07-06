package ecomhub.authservice.infrastructure.mapper;

import ecomhub.authservice.domain.model.Role;
import ecomhub.authservice.infrastructure.persistence.entity.RoleEntity;

public class RoleMapper {
    public static Role toDomain(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .build();
    }

    public static RoleEntity toEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
