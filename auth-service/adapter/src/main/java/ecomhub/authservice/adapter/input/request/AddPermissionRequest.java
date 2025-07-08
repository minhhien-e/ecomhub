package ecomhub.authservice.adapter.input.request;

import jakarta.validation.constraints.NotBlank;

public record AddPermissionRequest(
        @NotBlank String name,
        String description
) {
}
