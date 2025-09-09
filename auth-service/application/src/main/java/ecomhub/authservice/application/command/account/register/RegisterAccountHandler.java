package ecomhub.authservice.application.command.account.register;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.common.exception.concrete.account.EmailAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.account.PhoneNumberAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.account.UsernameAlreadyExistsException;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ecomhub.authservice.domain.constant.RoleKeyConstants.CUSTOMER;

@Service
@RequiredArgsConstructor
public class RegisterAccountHandler implements ICommandHandler<RegisterAccountCommand> {
    private final AccountRepositoryPort accountRepository;
    private final RoleRepositoryPort roleRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public void handle(RegisterAccountCommand command) {
        if (accountRepository.existsByIdentifier(command.getEmail())) {
            throw new EmailAlreadyExistsException(command.getEmail());
        }
        if (accountRepository.existsByIdentifier(command.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException(command.getPhoneNumber());
        }
        if (accountRepository.existsByIdentifier(command.getUsername())) {
            throw new UsernameAlreadyExistsException(command.getUsername());
        }
        var account = accountService.register(command.getUsername(), command.getEmail(), command.getPhoneNumber(), command.getPassword(), command.getPasswordConfirm(), command.getProvider(), roleRepository.getByKey(CUSTOMER));
        accountRepository.save(account);
    }

}
