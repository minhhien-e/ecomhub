package ecomhub.authservice.common.dto.request.role;

import java.util.UUID;

public record UpdateNameRoleRequest(UUID roleId, String newName) {
}
