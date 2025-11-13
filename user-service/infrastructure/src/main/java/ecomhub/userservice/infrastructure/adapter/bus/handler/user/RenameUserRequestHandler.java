package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.RenameUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.RenameUserUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RenameUserRequestHandler implements RequestHandler<MediatorCommand<Void, RenameUserCommand>, Void> {
    private final RenameUserUseCase renameUserUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, RenameUserCommand> request) {
        return renameUserUseCase.execute(request.command());
    }
}
