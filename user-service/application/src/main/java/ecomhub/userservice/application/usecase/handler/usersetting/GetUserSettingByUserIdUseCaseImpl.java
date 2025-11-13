package ecomhub.userservice.application.usecase.handler.usersetting;

import ecomhub.userservice.application.dto.query.usersetting.GetUserSettingByUserIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.application.port.in.usecase.usersetting.GetUserSettingByUserIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetUserSettingByUserIdUseCaseImpl implements GetUserSettingByUserIdUseCase {
    @Override
    public UserSettingReadModel execute(GetUserSettingByUserIdQuery params) {
        return null;
    }
}
