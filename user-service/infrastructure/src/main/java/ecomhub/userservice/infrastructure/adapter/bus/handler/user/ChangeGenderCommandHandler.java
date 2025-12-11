package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeGenderCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeGenderUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeGenderCommandHandler implements RequestHandler<BusRequestWrapper<ChangeGenderCommand>, Void> {
    private final ChangeGenderUseCase changeGenderUseCase;

    @Override
    public Void execute(BusRequestWrapper<ChangeGenderCommand> request) {
        return changeGenderUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return ChangeGenderCommand.class;
    }
}
