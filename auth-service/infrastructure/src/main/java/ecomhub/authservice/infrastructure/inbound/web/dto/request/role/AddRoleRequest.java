package ecomhub.authservice.infrastructure.inbound.web.dto.request.role;

import java.util.Set;
import java.util.UUID;

public record AddRoleRequest(
        String name,
        String description,
        int level,
        Set<String> permissionKeys
) {
}
