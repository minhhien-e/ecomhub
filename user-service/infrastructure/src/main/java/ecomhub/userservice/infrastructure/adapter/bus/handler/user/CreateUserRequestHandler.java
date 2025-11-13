package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.CreateUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.CreateUserUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateUserRequestHandler implements RequestHandler<MediatorCommand<Void, CreateUserCommand>, Void> {
    private final CreateUserUseCase createUserUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, CreateUserCommand> request) {
        return createUserUseCase.execute(request.command());
    }
}
