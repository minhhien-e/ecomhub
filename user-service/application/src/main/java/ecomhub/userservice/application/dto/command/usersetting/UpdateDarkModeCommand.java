package ecomhub.userservice.application.dto.command.usersetting;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record UpdateDarkModeCommand(UUID userId,Boolean isDarkMode) implements Command<Void> {
}
