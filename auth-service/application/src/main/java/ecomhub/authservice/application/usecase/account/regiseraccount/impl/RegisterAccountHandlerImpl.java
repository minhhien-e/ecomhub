package ecomhub.authservice.application.usecase.account.regiseraccount.impl;

import ecomhub.authservice.application.mapper.AccountCommandMapper;
import ecomhub.authservice.application.ports.repositories.AccountRepository;
import ecomhub.authservice.application.usecase.account.regiseraccount.RegisterAccountCommand;
import ecomhub.authservice.application.usecase.account.regiseraccount.RegisterAccountHandler;
import ecomhub.authservice.common.exception.AccountAlreadyExistsException;
import ecomhub.authservice.domain.service.PasswordHashService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAccountHandlerImpl implements RegisterAccountHandler {
    private final AccountRepository accountRepository;
    private final AccountCommandMapper accountCommandMapper;
    private final PasswordHashService passwordHashService;

    @Override
    @Transactional
    public void execute(RegisterAccountCommand command) {
        if (accountRepository.isAccountExists(command.email())) {
            throw new AccountAlreadyExistsException(String.format("Email %s đã tồn tại", command.email()));
        }
        if (accountRepository.isAccountExists(command.username())) {
            throw new AccountAlreadyExistsException(String.format("Username %s đã tồn tại", command.username()));
        }
        if (accountRepository.isAccountExists(command.phoneNumber())) {
            throw new AccountAlreadyExistsException(String.format("Số điện thoại %s đã tồn tại", command.phoneNumber()));
        }
        var account = accountCommandMapper.toDomain(command);
        account.setPasswordHash(passwordHashService.hash(command.password()));
        account.validateForRegistration();
        accountRepository.save(account);
    }
}
