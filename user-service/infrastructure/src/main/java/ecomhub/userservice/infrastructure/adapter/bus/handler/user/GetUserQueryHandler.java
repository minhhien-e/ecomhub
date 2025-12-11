package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ecomhub.userservice.application.dto.query.user.GetUserQuery;
import ecomhub.userservice.application.port.in.usecase.user.GetUserUseCase;
import ecomhub.userservice.domain.aggregate.User;

@Component
@RequiredArgsConstructor
public class GetUserQueryHandler implements RequestHandler<BusRequestWrapper<GetUserQuery>, User> {
    private final GetUserUseCase getUserUseCase;

    @Override
    public User execute(BusRequestWrapper<GetUserQuery> request) {
        return getUserUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return GetUserQuery.class;
    }
}
