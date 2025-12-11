package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeAvatarCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeAvatarUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeAvatarCommandHandler implements RequestHandler<BusRequestWrapper<ChangeAvatarCommand>, Void> {
    private final ChangeAvatarUseCase changeAvatarUseCase;

    @Override
    public Void execute(BusRequestWrapper<ChangeAvatarCommand> request) {
        return changeAvatarUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return ChangeAvatarCommand.class;
    }
}
