package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.RenameUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.RenameUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class RenameUserUseCaseImpl implements RenameUserUseCase {
    @Override
    public Void execute(RenameUserCommand params) {
        return null;
    }
}
