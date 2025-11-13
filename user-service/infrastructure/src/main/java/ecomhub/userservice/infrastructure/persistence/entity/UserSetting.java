package ecomhub.userservice.infrastructure.persistence.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "user_settings")
@Data
@Builder
public class UserSetting {

    @Id
    private UUID id;

    @NotBlank
    private String userId;

    @NotBlank
    @Size(max = 10)
    private String language;

    private boolean receiveMarketingEmail;

    private boolean darkMode;

    private LocalDateTime updatedAt;

    // Enum cho ngôn ngữ
    public enum Language {
        VI, EN
    }
}
