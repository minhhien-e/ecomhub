package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;

import java.util.UUID;

public record ChangeGenderCommand(UUID userId, String newGender) implements Request<Void> {
}
