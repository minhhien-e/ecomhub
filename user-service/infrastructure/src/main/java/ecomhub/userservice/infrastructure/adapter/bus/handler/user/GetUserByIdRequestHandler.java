package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.application.port.in.usecase.user.GetUserByIdUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorQuery;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetUserByIdRequestHandler implements RequestHandler<MediatorQuery<UserReadModel, GetUserByIdQuery>, UserReadModel> {
    private final GetUserByIdUseCase getUserByIdUseCase;

    @Override
    @Transactional
    public UserReadModel handle(MediatorQuery<UserReadModel, GetUserByIdQuery> request) {
        return getUserByIdUseCase.execute(request.query());
    }
}
