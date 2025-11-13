package ecomhub.userservice.api.dto.response.usersetting;

import java.time.LocalDateTime;

public record UserSettingResponse(String id,
                                  String userId,
                                  String language,
                                  Boolean receiveMarketingEmail,
                                  Boolean darkMode,
                                  LocalDateTime updatedAt) {
}
