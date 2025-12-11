package ecomhub.userservice.infrastructure.persistence.entity;

import io.github.infrastructure.mongo.entity.base.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@Data
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends MongoEntity {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDate;
    private String avatarUrl;
    private List<String> roles;
    private boolean isActive;
    private UserSettingEntity setting;
    private LocalDateTime createdAt;
}
