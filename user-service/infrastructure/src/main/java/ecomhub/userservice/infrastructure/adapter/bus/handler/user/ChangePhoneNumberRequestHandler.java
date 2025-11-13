package ecomhub.userservice.infrastructure.adapter.bus.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangePhoneNumberCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangePhoneNumberUseCase;
import ecomhub.userservice.infrastructure.adapter.bus.MediatorCommand;
import io.github.admiralxy.mediatr.api.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChangePhoneNumberRequestHandler implements RequestHandler<MediatorCommand<Void, ChangePhoneNumberCommand>, Void> {
    private final ChangePhoneNumberUseCase changePhoneNumberUseCase;

    @Override
    @Transactional
    public Void handle(MediatorCommand<Void, ChangePhoneNumberCommand> request) {
        return changePhoneNumberUseCase.execute(request.command());
    }
}
