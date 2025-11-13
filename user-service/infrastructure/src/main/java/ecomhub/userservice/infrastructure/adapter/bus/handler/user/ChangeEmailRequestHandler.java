package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeEmailCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeEmailUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChangeEmailRequestHandler implements RequestHandler<MediatorCommand<Void, ChangeEmailCommand>, Void> {
    private final ChangeEmailUseCase changeEmailUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, ChangeEmailCommand> request) {
        return changeEmailUseCase.execute(request.command());
    }
}
