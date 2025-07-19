package ecomhub.authservice.adapter.output.security;

import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.application.port.security.LoadAccountByIdentifierPort;
import ecomhub.authservice.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoadAccountByIdentifier implements LoadAccountByIdentifierPort {
    private final AccountRepositoryPort accountRepository;

    @Override
    public Optional<Account> loadByIdentifier(String identifier) {
        return accountRepository.findByIdentifier(identifier);
    }
}
