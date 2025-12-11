package ecomhub.userservice.application.dto.readmodel;

import java.time.LocalDateTime;

public record UserSettingReadModel(
        String language,
        boolean darkMode,
        boolean receiveMarketingEmail,
        LocalDateTime updatedAt
) {
}
