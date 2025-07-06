package ecomhub.authservice.application.usecase.account.impl;

import ecomhub.authservice.application.command.RegisterAccountCommand;
import ecomhub.authservice.application.mapper.AccountMapper;
import ecomhub.authservice.application.usecase.account.RegisterAccountUseCase;
import ecomhub.authservice.domain.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public void execute(RegisterAccountCommand command) {
        accountService.registerAccount(accountMapper.toDomain(command));
    }
}
