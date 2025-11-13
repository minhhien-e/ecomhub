package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateLanguageCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateLanguageUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateLanguageRequestHandler implements RequestHandler<MediatorCommand<Void, UpdateLanguageCommand>, Void> {
    private final UpdateLanguageUseCase updateLanguageUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, UpdateLanguageCommand> request) {
        return updateLanguageUseCase.execute(request.command());
    }
}
