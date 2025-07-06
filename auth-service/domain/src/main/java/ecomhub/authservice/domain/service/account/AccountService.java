package ecomhub.authservice.domain.service.account;

import ecomhub.authservice.domain.model.Account;

public interface AccountService {
    void registerAccount(Account account);
    boolean isAccountExists(String identifier);
}
