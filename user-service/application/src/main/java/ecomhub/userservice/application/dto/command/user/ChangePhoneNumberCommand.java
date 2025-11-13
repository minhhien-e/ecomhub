package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record ChangePhoneNumberCommand(UUID userId, String newPhoneNumber) implements Command<Void> {
}
