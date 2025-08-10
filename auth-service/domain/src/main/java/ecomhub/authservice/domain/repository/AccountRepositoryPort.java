package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryPort {
    void save(Account account);
    Optional<Account> findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);

    Optional<Account> findById(UUID id);
}
