package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateDarkModeCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateDarkModeUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDarkModeCommandHandler implements RequestHandler<BusRequestWrapper<UpdateDarkModeCommand>, Void> {
    private final UpdateDarkModeUseCase updateDarkModeUseCase;

    @Override
    public Void execute(BusRequestWrapper<UpdateDarkModeCommand> request) {
        return updateDarkModeUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return UpdateDarkModeCommand.class;
    }
}
