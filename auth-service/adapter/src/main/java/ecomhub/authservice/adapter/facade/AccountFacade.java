package ecomhub.authservice.adapter.facade;

import ecomhub.authservice.application.command.RegisterAccountCommand;
import ecomhub.authservice.application.usecase.account.RegisterAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountFacade {
    private final RegisterAccountUseCase registerAccountUseCase;

    public void registerAccount(RegisterAccountCommand command) {
        registerAccountUseCase.execute(command);
    }

}
