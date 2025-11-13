package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateDarkModeCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateDarkModeUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateDarkModeRequestHandler implements RequestHandler<MediatorCommand<Void, UpdateDarkModeCommand>, Void> {
    private final UpdateDarkModeUseCase updateDarkModeUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, UpdateDarkModeCommand> request) {
        return updateDarkModeUseCase.execute(request.command());
    }
}
