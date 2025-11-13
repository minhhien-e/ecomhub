package ecomhub.userservice.application.usecase.handler.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateMarketingEmailCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateMarketingEmailUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateMarketingEmailUseCaseImpl implements UpdateMarketingEmailUseCase {
    @Override
    public Void execute(UpdateMarketingEmailCommand params) {
        return null;
    }
}
