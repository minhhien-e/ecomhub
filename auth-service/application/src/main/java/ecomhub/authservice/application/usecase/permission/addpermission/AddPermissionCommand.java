package ecomhub.authservice.application.usecase.permission.addpermission;

import jakarta.validation.constraints.NotBlank;

public record AddPermissionCommand(
        @NotBlank String name,
        String description
) {
}
