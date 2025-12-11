package ecomhub.userservice.infrastructure.adapter.bus.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateMarketingEmailCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateMarketingEmailUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateMarketingEmailCommandHandler implements RequestHandler<BusRequestWrapper<UpdateMarketingEmailCommand>, Void> {
    private final UpdateMarketingEmailUseCase updateMarketingEmailUseCase;

    @Override
    public Void execute(BusRequestWrapper<UpdateMarketingEmailCommand> request) {
        return updateMarketingEmailUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return UpdateMarketingEmailCommand.class;
    }
}
