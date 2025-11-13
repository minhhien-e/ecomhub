package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.command.user.ChangePhoneNumberCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangePhoneNumberUseCase;
import org.springframework.stereotype.Service;

@Service
public class ChangePhoneNumberUseCaseImpl implements ChangePhoneNumberUseCase {
    @Override
    public Void execute(ChangePhoneNumberCommand params) {
        return null;
    }
}
