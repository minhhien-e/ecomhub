package ecomhub.authservice.adapter.input.facade;

import ecomhub.authservice.adapter.input.request.RegisterBasicRequest;
import ecomhub.authservice.adapter.input.mapper.AccountRequestMapper;
import ecomhub.authservice.application.usecase.account.regiseraccount.RegisterAccountCommand;
import ecomhub.authservice.application.usecase.account.regiseraccount.RegisterAccountHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountFacade {
    private final RegisterAccountHandler registerAccountUseCase;
    private final AccountRequestMapper accountRequestMapper;

    public void registerAccount(RegisterBasicRequest request) {
        RegisterAccountCommand registerAccountCommand = accountRequestMapper.toCommand(request);
        registerAccountUseCase.execute(registerAccountCommand);
    }

}
