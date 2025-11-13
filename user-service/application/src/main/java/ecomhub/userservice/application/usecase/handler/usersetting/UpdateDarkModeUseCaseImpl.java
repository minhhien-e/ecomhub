package ecomhub.userservice.application.usecase.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateDarkModeCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateDarkModeUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateDarkModeUseCaseImpl implements UpdateDarkModeUseCase {
    @Override
    public Void execute(UpdateDarkModeCommand params) {
        return null;
    }
}
