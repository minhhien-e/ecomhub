package ecomhub.userservice.application.usecase.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateLanguageCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateLanguageUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateLanguageUseCaseImpl implements UpdateLanguageUseCase {
    @Override
    public Void execute(UpdateLanguageCommand params) {
        return null;
    }
}
