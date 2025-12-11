package ecomhub.userservice.domain.repository;

import ecomhub.userservice.domain.entity.UserSetting;
import io.github.domain.repository.DomainEntityRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSettingRepository extends DomainEntityRepository<UserSetting> {
    Optional<UserSetting> findByUserId(UUID userId);
}
