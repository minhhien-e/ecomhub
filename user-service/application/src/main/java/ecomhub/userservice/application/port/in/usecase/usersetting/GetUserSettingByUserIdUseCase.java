package ecomhub.userservice.application.port.in.usecase.usersetting;

import ecomhub.userservice.application.dto.query.usersetting.GetUserSettingByUserIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface GetUserSettingByUserIdUseCase extends UseCase<UserSettingReadModel, GetUserSettingByUserIdQuery> {
}
