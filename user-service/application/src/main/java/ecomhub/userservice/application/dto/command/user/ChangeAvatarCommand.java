package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record ChangeAvatarCommand(UUID userId, String newAvatarUrl) implements Command<Void> {
}
