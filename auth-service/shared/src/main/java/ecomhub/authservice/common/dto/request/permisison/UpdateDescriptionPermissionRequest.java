package ecomhub.authservice.common.dto.request.permisison;

import java.util.UUID;

public record UpdateDescriptionPermissionRequest(UUID permissionId,String newDescription) {
}
