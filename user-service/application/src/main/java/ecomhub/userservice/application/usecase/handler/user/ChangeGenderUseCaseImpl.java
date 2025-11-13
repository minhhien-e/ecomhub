package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeGenderCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeGenderUseCase;
import org.springframework.stereotype.Service;

@Service
public class ChangeGenderUseCaseImpl implements ChangeGenderUseCase {
    @Override
    public Void execute(ChangeGenderCommand params) {
        return null;
    }
}
