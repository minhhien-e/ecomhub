package ecomhub.userservice.application.dto.command.usersetting;

import ecomhub.userservice.application.dto.base.Request;

import java.util.UUID;

public record UpdateLanguageCommand(UUID userId,String newLanguage) implements Request<Void> {
}
