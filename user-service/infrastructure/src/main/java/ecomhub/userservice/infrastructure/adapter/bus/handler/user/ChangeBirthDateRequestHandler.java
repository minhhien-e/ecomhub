package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeBirthDateCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeBirthDateUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChangeBirthDateRequestHandler implements RequestHandler<MediatorCommand<Void, ChangeBirthDateCommand>, Void> {
    private final ChangeBirthDateUseCase changeBirthDateUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, ChangeBirthDateCommand> request) {
        return changeBirthDateUseCase.execute(request.command());
    }
}
