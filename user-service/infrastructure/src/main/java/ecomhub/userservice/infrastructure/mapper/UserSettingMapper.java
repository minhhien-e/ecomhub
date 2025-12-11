package ecomhub.userservice.infrastructure.mapper;

import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.domain.entity.UserSetting;
import ecomhub.userservice.infrastructure.persistence.entity.UserSettingEntity;

public class UserSettingMapper {
    public static UserSettingEntity toEntity(UserSetting domain) {
        if (domain == null) return null;
        return UserSettingEntity.builder()
                .language(domain.getLanguage())
                .darkMode(domain.isDarkMode())
                .receiveMarketingEmail(domain.isReceiveMarketingEmail())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static UserSetting toDomain(UserSettingEntity entity) {
        if (entity == null) return null;
        return UserSetting.reconstruct(
                entity.getLanguage(),
                entity.isDarkMode(),
                entity.isReceiveMarketingEmail(),
                entity.getUpdatedAt()
        );
    }

    public static UserSettingReadModel toReadModel(UserSettingEntity entity) {
        return new UserSettingReadModel(
                entity.getLanguage(),
                entity.isDarkMode(),
                entity.isReceiveMarketingEmail(),
                entity.getUpdatedAt()
        );
    }
}
