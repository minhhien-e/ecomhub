package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;

import java.util.UUID;

public record RenameUserCommand(UUID userId, String newName) implements Request<Void> {
}
