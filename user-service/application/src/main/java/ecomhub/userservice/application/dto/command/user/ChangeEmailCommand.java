package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;

import java.util.UUID;

public record ChangeEmailCommand(UUID userId, String newEmail) implements Request<Void> {
}
