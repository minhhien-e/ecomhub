package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ecomhub.userservice.application.dto.command.user.UpdateUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.UpdateUserUseCase;

@Component
@RequiredArgsConstructor
public class UpdateUserCommandHandler implements RequestHandler<BusRequestWrapper<UpdateUserCommand>, Void> {
    private final UpdateUserUseCase updateUserUseCase;

    @Override
    public Void execute(BusRequestWrapper<UpdateUserCommand> request) {
        return updateUserUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return UpdateUserCommand.class;
    }
}
