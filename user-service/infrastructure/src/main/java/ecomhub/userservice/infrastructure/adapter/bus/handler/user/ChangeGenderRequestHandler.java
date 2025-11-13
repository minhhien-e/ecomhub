package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeGenderCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeGenderUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChangeGenderRequestHandler implements RequestHandler<MediatorCommand<Void, ChangeGenderCommand>, Void> {
    private final ChangeGenderUseCase changeGenderUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, ChangeGenderCommand> request) {
        return changeGenderUseCase.execute(request.command());
    }
}
