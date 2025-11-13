package ecomhub.userservice.infrastructure.persistence.repository.usersetting;

import ecomhub.userservice.infrastructure.persistence.entity.UserSetting;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserSettingRepository extends MongoRepository<UserSetting, UUID> {
}
