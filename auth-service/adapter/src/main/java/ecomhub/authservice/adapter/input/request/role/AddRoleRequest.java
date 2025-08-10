package ecomhub.authservice.adapter.input.request.role;

import java.util.Set;
import java.util.UUID;

public record AddRoleRequest(
        String name,
        String description,
        Set<String> permissionKeys
) {
}
