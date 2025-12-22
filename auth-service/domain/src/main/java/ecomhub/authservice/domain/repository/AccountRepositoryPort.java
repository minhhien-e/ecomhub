package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryPort {
    void save(Account account);

    Optional<Account> findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    Account geById(UUID id);

    void assignRole(UUID accountId, UUID roleId);

    void revokeRole(UUID accountId, UUID roleId);
}
