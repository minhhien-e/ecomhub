package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangePhoneNumberCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangePhoneNumberUseCase;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangePhoneNumberCommandHandler implements RequestHandler<BusRequestWrapper<ChangePhoneNumberCommand>, Void> {
    private final ChangePhoneNumberUseCase changePhoneNumberUseCase;

    @Override
    public Void execute(BusRequestWrapper<ChangePhoneNumberCommand> request) {
        return changePhoneNumberUseCase.execute(request.request());
    }

    @Override
    public Class<?> getRequestClass() {
        return ChangePhoneNumberCommand.class;
    }
}
