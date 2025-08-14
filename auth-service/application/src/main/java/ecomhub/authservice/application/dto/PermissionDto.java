package ecomhub.authservice.application.dto;

import ecomhub.authservice.common.dto.response.PermissionResponse;
import ecomhub.authservice.domain.valueobject.Name;
import ecomhub.authservice.domain.valueobject.PermissionKey;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PermissionDto {
    private UUID id;
    private Name name;
    private PermissionKey key;
    private String description;

    public PermissionResponse toResponse() {
        return new PermissionResponse(id, name.getValue(), key.getValue(), description);
    }
}
