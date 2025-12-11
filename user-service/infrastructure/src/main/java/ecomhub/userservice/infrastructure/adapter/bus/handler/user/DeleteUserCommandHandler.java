package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ecomhub.userservice.application.dto.command.user.DeleteUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.DeleteUserUseCase;

@Component
@RequiredArgsConstructor
public class DeleteUserCommandHandler implements RequestHandler<BusRequestWrapper<DeleteUserCommand>, Void> {
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public Void execute(BusRequestWrapper<DeleteUserCommand> request) {
        return deleteUserUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return DeleteUserCommand.class;
    }
}
