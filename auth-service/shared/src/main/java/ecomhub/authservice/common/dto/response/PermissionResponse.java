package ecomhub.authservice.common.dto.response;

import java.util.UUID;

public record PermissionResponse(UUID id, String name, String key, String description) {
}
