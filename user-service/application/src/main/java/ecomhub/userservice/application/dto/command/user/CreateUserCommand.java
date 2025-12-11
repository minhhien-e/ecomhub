package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;

import java.util.List;
import java.util.UUID;

public record CreateUserCommand(
        UUID userId,
        String username,
        String email,
        String password,
        List<String> roles
) implements Request<Void> {
}
