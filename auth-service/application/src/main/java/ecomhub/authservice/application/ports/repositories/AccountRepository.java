package ecomhub.authservice.application.ports.repositories;

import ecomhub.authservice.domain.entities.Account;

public interface AccountRepository {
    void save(Account account);
    Account findByIdentifier(String identifier);
    boolean isAccountExists(String identifier);

}
