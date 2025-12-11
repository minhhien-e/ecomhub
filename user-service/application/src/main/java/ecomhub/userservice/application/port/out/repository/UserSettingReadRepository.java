package ecomhub.userservice.application.port.out.repository;

import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;

import java.util.UUID;

public interface UserSettingReadRepository {
    UserSettingReadModel getByUserId(UUID userId);
}
