package ecomhub.authservice.common.dto.request.permisison;

public record AddPermissionRequest(
        String name,
        String description,
        String key
) {
}
