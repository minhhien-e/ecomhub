package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.base.Request;
import java.time.LocalDate;
import java.util.UUID;

public record ChangeBirthDateCommand(UUID userId,LocalDate newBirthDate) implements Request<Void> {
}
