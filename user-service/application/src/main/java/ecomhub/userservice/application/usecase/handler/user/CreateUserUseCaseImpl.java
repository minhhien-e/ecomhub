package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.CreateUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.CreateUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    @Override
    public Void execute(CreateUserCommand params) {
        return null;
    }
}
