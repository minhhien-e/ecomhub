package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ecomhub.userservice.application.dto.command.user.CreateUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.CreateUserUseCase;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler implements RequestHandler<BusRequestWrapper<CreateUserCommand>, Void> {
    private final CreateUserUseCase createUserUseCase;

    @Override
    public Void execute(BusRequestWrapper<CreateUserCommand> request) {
        return createUserUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return CreateUserCommand.class;
    }
}
