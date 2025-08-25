package ecomhub.authservice.common.dto.request.role;

import java.util.Set;
import java.util.UUID;

public record AddRoleRequest(
        String name,
        String key,
        String type,
        String description,
        int level,
        Set<String> permissionKeys
) {
}
