package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangeBirthDateCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeBirthDateUseCase;
import org.springframework.stereotype.Service;

@Service
public class ChangeBirthDateUseCaseImpl implements ChangeBirthDateUseCase {
    @Override
    public Void execute(ChangeBirthDateCommand params) {
        return null;
    }
}
