package ecomhub.authservice.adapter.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddPermissionRequest(
        @NotBlank String name,
        String description
) {
}
