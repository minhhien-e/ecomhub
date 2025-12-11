package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.application.port.in.usecase.user.GetUserByIdUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserByIdQueryHandler implements RequestHandler<BusRequestWrapper<GetUserByIdQuery>, UserReadModel> {
    private final GetUserByIdUseCase getUserByIdUseCase;

    @Override
    public UserReadModel execute(BusRequestWrapper<GetUserByIdQuery> request) {
        return getUserByIdUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return GetUserByIdQuery.class;
    }
}
