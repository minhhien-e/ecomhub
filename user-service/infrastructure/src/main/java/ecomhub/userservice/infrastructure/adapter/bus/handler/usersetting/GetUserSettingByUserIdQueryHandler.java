package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.query.usersetting.GetUserSettingByUserIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.application.port.in.usecase.usersetting.GetUserSettingByUserIdUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserSettingByUserIdQueryHandler implements RequestHandler<BusRequestWrapper<GetUserSettingByUserIdQuery>, UserSettingReadModel> {
    private final GetUserSettingByUserIdUseCase getUserSettingByUserIdUseCase;

    @Override
    public UserSettingReadModel execute(BusRequestWrapper<GetUserSettingByUserIdQuery> request) {
        return getUserSettingByUserIdUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return GetUserSettingByUserIdQuery.class;
    }
}
