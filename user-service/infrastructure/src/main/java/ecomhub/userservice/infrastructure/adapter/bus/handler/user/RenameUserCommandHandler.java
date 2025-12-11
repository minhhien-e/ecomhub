package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.RenameUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.RenameUserUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RenameUserCommandHandler implements RequestHandler<BusRequestWrapper<RenameUserCommand>, Void> {
    private final RenameUserUseCase renameUserUseCase;

    @Override
    public Void execute(BusRequestWrapper<RenameUserCommand> request) {
        return renameUserUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return RenameUserCommand.class;
    }
}
