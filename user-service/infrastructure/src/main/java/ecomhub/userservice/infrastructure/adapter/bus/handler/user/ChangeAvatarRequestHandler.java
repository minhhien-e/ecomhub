package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeAvatarCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeAvatarUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChangeAvatarRequestHandler implements RequestHandler<MediatorCommand<Void, ChangeAvatarCommand>, Void> {
    private final ChangeAvatarUseCase changeAvatarUseCase;
    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, ChangeAvatarCommand> request) {
        return changeAvatarUseCase.execute(request.command());
    }
}
