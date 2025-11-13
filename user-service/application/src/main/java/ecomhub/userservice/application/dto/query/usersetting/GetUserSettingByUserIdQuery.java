package ecomhub.userservice.application.dto.query.usersetting;

import ecomhub.userservice.application.dto.query.base.Query;
import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;

import java.util.UUID;

public record GetUserSettingByUserIdQuery(UUID userId) implements Query<UserSettingReadModel> {
}
