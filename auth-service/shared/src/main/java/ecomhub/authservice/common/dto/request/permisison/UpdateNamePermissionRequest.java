package ecomhub.authservice.common.dto.request.permisison;

import java.util.UUID;

public record UpdateNamePermissionRequest(UUID permissionId, String newName) {
}
