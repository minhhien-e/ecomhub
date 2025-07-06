package ecomhub.authservice.adapter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record AddRoleRequest(
        @NotBlank String name,
        String description,
        @NotEmpty Set<@NotNull UUID> permissionIds
) {
}
