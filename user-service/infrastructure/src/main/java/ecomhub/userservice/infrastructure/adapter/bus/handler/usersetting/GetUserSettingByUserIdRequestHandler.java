package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.query.usersetting.GetUserSettingByUserIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.application.port.in.usecase.usersetting.GetUserSettingByUserIdUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorQuery;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetUserSettingByUserIdRequestHandler implements RequestHandler<MediatorQuery<UserSettingReadModel, GetUserSettingByUserIdQuery>, UserSettingReadModel> {
    private final GetUserSettingByUserIdUseCase getUserSettingByUserIdUseCase;

    @Override
    @Transactional
    public UserSettingReadModel handle(MediatorQuery<UserSettingReadModel, GetUserSettingByUserIdQuery> request) {
        return getUserSettingByUserIdUseCase.execute(request.query());
    }
}
