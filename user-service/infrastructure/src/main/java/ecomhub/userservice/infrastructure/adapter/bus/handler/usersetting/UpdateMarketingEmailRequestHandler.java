package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateMarketingEmailCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateMarketingEmailUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateMarketingEmailRequestHandler implements RequestHandler<MediatorCommand<Void, UpdateMarketingEmailCommand>, Void> {
    private final UpdateMarketingEmailUseCase updateMarketingEmailUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, UpdateMarketingEmailCommand> request) {
        return updateMarketingEmailUseCase.execute(request.command());
    }
}
