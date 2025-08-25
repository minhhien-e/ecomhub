package ecomhub.authservice.common.dto.response;

import java.util.Set;
import java.util.UUID;

public record RoleResponse(UUID id, String name,
                           String key,
                           String type,
                           String status,
                           String description,
                           int level,
                           Set<PermissionResponse> permissions
) {
}
