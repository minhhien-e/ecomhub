package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;
import java.util.List;
import java.util.UUID;

public record UpdateUserCommand(
    UUID userId,
    String password,
    List<String> roles,
    Boolean isActive
) implements Request<Void> {}
