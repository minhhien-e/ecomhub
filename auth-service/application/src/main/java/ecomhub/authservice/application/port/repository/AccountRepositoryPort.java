package ecomhub.authservice.application.port.repository;

import ecomhub.authservice.domain.entity.Account;

import java.util.Optional;

public interface AccountRepositoryPort {
    Account save(Account account);
    Optional<Account> findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);

}
