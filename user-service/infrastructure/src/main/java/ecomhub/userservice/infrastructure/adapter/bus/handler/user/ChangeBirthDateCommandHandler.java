package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeBirthDateCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeBirthDateUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeBirthDateCommandHandler implements RequestHandler<BusRequestWrapper<ChangeBirthDateCommand>, Void> {
    private final ChangeBirthDateUseCase changeBirthDateUseCase;

    @Override
    public Void execute(BusRequestWrapper<ChangeBirthDateCommand> request) {
        return changeBirthDateUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return ChangeBirthDateCommand.class;
    }
}
