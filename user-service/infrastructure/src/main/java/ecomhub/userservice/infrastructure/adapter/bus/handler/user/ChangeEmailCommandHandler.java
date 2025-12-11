package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeEmailCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeEmailUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeEmailCommandHandler implements RequestHandler<BusRequestWrapper<ChangeEmailCommand>, Void> {
    private final ChangeEmailUseCase changeEmailUseCase;

    @Override
    public Void execute(BusRequestWrapper<ChangeEmailCommand> request) {
        return changeEmailUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return ChangeEmailCommand.class;
    }
}
