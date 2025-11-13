package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeEmailCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeEmailUseCase;
import org.springframework.stereotype.Service;

@Service
public class ChangeEmailUseCaseImpl implements ChangeEmailUseCase {
    @Override
    public Void execute(ChangeEmailCommand params) {
        return null;
    }
}
