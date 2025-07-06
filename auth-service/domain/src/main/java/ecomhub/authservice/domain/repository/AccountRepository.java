package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.model.Account;

public interface AccountRepository {
    void save(Account account);
    Account findByIdentifier(String identifier);
    boolean isAccountExists(String identifier);

}
