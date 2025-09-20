package ecomhub.authservice.application.dto;

import ecomhub.authservice.common.dto.response.RoleResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class RoleDto {
    private UUID id;
    private String name;
    private String key;
    private int level;
    private String type;
    private String status;
    private String description;
    private Set<PermissionDto> permissions;

    public RoleResponse toResponse() {
        return new RoleResponse(id, name,
                key, type, status,
                description, level, permissions
                .stream().map(PermissionDto::toResponse)
                .toList());
    }
}
