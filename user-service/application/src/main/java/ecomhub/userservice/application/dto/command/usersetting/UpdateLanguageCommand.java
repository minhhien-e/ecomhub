package ecomhub.userservice.application.dto.command.usersetting;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record UpdateLanguageCommand(UUID userId,String newLanguage) implements Command<Void> {
}
