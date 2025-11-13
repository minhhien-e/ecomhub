package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record ChangeEmailCommand(UUID userId, String newEmail) implements Command<Void> {
}
