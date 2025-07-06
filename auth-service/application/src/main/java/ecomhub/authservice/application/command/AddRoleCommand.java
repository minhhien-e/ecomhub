package ecomhub.authservice.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record AddRoleCommand(
        @NotBlank String name,
        String description,
        @NotEmpty Set<@NotNull UUID> permissionIds
) {
}
