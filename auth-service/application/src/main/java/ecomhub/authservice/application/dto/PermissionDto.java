package ecomhub.authservice.application.dto;

import ecomhub.authservice.common.dto.response.PermissionResponse;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PermissionDto {
    private UUID id;
    private String name;
    private String key;
    private String description;

    public PermissionResponse toResponse() {
        return new PermissionResponse(id, name, key, description);
    }
}
