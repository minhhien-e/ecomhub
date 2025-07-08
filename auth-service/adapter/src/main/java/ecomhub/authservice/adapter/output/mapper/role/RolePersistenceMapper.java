package ecomhub.authservice.adapter.output.mapper.role;

import ecomhub.authservice.domain.entities.Role;
import ecomhub.authservice.adapter.output.persistence.entity.RoleEntity;

public class RolePersistenceMapper {
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
