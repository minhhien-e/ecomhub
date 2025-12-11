package ecomhub.userservice.application.dto.command.usersetting;

import ecomhub.userservice.application.dto.base.Request;
import java.util.UUID;

public record UpdateDarkModeCommand(UUID userId,Boolean isDarkMode) implements Request<Void> {
}
