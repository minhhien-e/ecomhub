package ecomhub.authservice.application.command;

import jakarta.validation.constraints.NotBlank;

public record AddPermissionCommand(
        @NotBlank String name,
        String description
) {
}
