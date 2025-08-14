package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.permission.add.AddPermissionCommand;
import ecomhub.authservice.application.dto.PermissionDto;
import ecomhub.authservice.domain.entity.Permission;

public final class PermissionMapper {
    public static PermissionDto toDto(Permission permission) {
        return PermissionDto.builder()
                .id(permission.getId())
                .name(permission.getName().getValue())
                .key(permission.getKey().getValue())
                .description(permission.getDescription().orElse(null))
                .build();
    }

    public static Permission toDomain(AddPermissionCommand addPermissionCommand) {
        return new Permission(addPermissionCommand.getName(), addPermissionCommand.getKey(), addPermissionCommand.getDescription());
    }
}
