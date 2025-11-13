package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record RenameUserCommand(UUID userId, String newName) implements Command<Void> {
}
