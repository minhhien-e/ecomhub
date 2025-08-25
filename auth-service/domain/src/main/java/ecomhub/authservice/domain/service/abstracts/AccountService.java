package ecomhub.authservice.domain.service.abstracts;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;

public interface AccountService {
    void assignRole(Account targetAccount, Role targetRole, Account requester);

    void revokeRole(Account targetAccount, Role targetRole, Account requester);

    void register(Account account, Role role);
}
