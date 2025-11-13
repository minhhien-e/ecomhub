package ecomhub.userservice.application.dto.command.usersetting;

import ecomhub.userservice.application.dto.command.base.Command;

import java.util.UUID;

public record UpdateMarketingEmailCommand(UUID userId, Boolean receiveMarketingEmail)  implements Command<Void> {
}
