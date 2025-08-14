package ecomhub.authservice.application.dto;

import ecomhub.authservice.common.dto.response.RoleResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Data
public class RoleDto {
    private UUID id;
    private String name;
    private String description;
    private Set<PermissionDto> permissions;
    private boolean active;
    private int level;

    public RoleResponse toResponse() {
        return new RoleResponse(id, name, description, permissions
                .stream().map(PermissionDto::toResponse)
                .collect(Collectors.toSet()), active, level);
    }
}
