package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

import java.time.LocalDate;
import java.util.UUID;

public record ChangeBirthDateCommand(UUID userId,LocalDate newBirthDate) implements Command<Void> {
}
