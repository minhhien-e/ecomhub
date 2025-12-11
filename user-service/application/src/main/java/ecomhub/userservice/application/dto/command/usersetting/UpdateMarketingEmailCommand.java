package ecomhub.userservice.application.dto.command.usersetting;

import ecomhub.userservice.application.dto.base.Request;
import java.util.UUID;

public record UpdateMarketingEmailCommand(UUID userId, Boolean receiveMarketingEmail)  implements Request<Void> {
}
