package ecomhub.userservice.infrastructure.persistence.entity;

import io.github.infrastructure.mongo.entity.base.MongoEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "user-settings")
@Data
@SuperBuilder
@NoArgsConstructor
public class UserSettingEntity extends MongoEntity {

    private String language;

    private boolean receiveMarketingEmail;

    private boolean darkMode;

    private LocalDateTime updatedAt;
}
