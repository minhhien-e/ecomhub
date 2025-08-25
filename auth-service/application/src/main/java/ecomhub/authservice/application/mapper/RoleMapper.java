package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.role.add.AddRoleCommand;
import ecomhub.authservice.application.dto.RoleDto;
import ecomhub.authservice.domain.entity.Role;

import java.util.stream.Collectors;

public final class RoleMapper {
    public static Role toDomain(AddRoleCommand command) {
        return new Role(command.getName(), command.getKey(), command.getType(), command.getDescription(), command.getLevel());
    }

    public static RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName().getValue())
                .key(role.getKey().getValue())
                .type(role.getType().getValue())
                .status(role.getStatus().getValue())
                .description(role.getDescription().orElse(null))
                .level(role.getLevel().getValue())
                .permissions(role.getPermissions().stream().map(PermissionMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
}
