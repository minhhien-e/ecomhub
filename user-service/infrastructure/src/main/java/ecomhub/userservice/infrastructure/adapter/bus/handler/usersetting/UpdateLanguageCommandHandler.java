package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateLanguageCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateLanguageUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateLanguageCommandHandler implements RequestHandler<BusRequestWrapper<UpdateLanguageCommand>, Void> {
    private final UpdateLanguageUseCase updateLanguageUseCase;

    @Override
    public Void execute(BusRequestWrapper<UpdateLanguageCommand> request) {
        return updateLanguageUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return UpdateLanguageCommand.class;
    }
}
