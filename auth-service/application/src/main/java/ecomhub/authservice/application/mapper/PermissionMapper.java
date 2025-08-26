package ecomhub.authservice.application.mapper;

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
}
