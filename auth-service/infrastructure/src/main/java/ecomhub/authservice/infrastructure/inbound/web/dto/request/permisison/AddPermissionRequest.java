package ecomhub.authservice.infrastructure.inbound.web.dto.request.permisison;

public record AddPermissionRequest(
        String name,
        String description,
        String key
) {
}
