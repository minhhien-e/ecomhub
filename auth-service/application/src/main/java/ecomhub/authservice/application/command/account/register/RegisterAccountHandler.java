package ecomhub.authservice.application.command.account.register;

import ecomhub.authservice.application.command.interfaces.ICommandHandler;
import ecomhub.authservice.application.enums.RoleName;
import ecomhub.authservice.application.mapper.AccountCommandMapper;
import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.concrete.account.conflict.EmailAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.account.conflict.PhoneNumberAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.account.conflict.UsernameAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.role.notfound.RoleNotFoundException;
import ecomhub.authservice.domain.service.PasswordHashService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterAccountHandler implements ICommandHandler<RegisterAccountCommand> {
    private final AccountRepositoryPort accountRepository;
    private final RoleRepositoryPort roleRepository;
    private final AccountCommandMapper accountCommandMapper;
    private final PasswordHashService passwordHashService;

    @Override
    @Transactional
    public void handle(RegisterAccountCommand command) {
        if (accountRepository.existsByIdentifier(command.getEmail())) {
            throw new EmailAlreadyExistsException(command.getEmail());
        }
        if (accountRepository.existsByIdentifier(command.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException(command.getEmail());
        }
        if (accountRepository.existsByIdentifier(command.getUsername())) {
            throw new UsernameAlreadyExistsException(command.getUsername());
        }
        //Hash password
        command.setPassword(passwordHashService.hash(command.getPassword()));
        var account = accountCommandMapper.toDomain(command);
        //Gán role
        Set<UUID> roleIds = getRoleIds(command.getRoles());
        roleIds.forEach(account::grantRole);
        accountRepository.save(account);
    }

    private Set<UUID> getRoleIds(List<RoleName> roleNames) {
        return roleNames.stream().map(name -> roleRepository.findIdByName(name.name())
                        .orElseThrow(() -> new RoleNotFoundException(name.name())))
                .collect(Collectors.toSet());
    }
}
