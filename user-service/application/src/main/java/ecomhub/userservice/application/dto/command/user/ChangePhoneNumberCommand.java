package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;

import java.util.UUID;

public record ChangePhoneNumberCommand(UUID userId, String newPhoneNumber) implements Request<Void> {
}
