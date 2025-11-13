package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record ChangeGenderCommand(UUID userId, String newGender)implements Command<Void> {
}
