package ecomhub.authservice.common.dto.response;

import java.util.Set;
import java.util.UUID;

public record RoleResponse(UUID id, String name, String description, Set<PermissionResponse> permissions,
                           boolean active, int level) {
}
