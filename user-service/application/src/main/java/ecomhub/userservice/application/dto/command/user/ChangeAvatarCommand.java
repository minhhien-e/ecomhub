package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;

import java.util.UUID;

public record ChangeAvatarCommand(UUID userId, String newAvatarUrl) implements Request<Void> {
}
