package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeAvatarCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeAvatarUseCase;
import org.springframework.stereotype.Service;

@Service
public class ChangeAvatarUseCaseImpl implements ChangeAvatarUseCase {
    @Override
    public Void execute(ChangeAvatarCommand params) {
        return null;
    }
}
