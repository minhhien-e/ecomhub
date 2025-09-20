package ecomhub.authservice.common.dto.request.role;

import java.util.Set;

public record GrantPermissionRequest(Set<String> permissionKeys) {
}
